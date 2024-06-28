package sk.vmproject.todoapp.presentation.todos

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sk.vmproject.todoapp.domain.TaskRepository
import sk.vmproject.todoapp.domain.utils.Result
import sk.vmproject.todoapp.domain.utils.asUiText

const val IS_FIRST_LAUNCH = "isFirstLaunch"

class TodosViewModel(
    private val taskRepository: TaskRepository,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    var todosState by mutableStateOf(TodosState())
        private set

    private val eventChannel = Channel<TodosEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            // check if the application is first time launched
            // if yes so, fetch Tasks from API and save to the Database
            val isFirstLaunch = sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)
            if (isFirstLaunch) {
                todosState = todosState.copy(isLoading = true)
                when (val fetchResult = taskRepository.fetchAllTasks()) {
                    is Result.Error -> {
                        eventChannel.send(TodosEvent.Error(error = fetchResult.error.asUiText()))
                        todosState = todosState.copy(isLoading = false)
                    }

                    is Result.Success -> {
                        sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
                        todosState = todosState.copy(isLoading = false)
                    }
                }
            }
        }

        taskRepository.getAllTasks().onEach {
            todosState = todosState.copy(todos = it)
        }.launchIn(viewModelScope)
    }

    fun onTodosAction(action: TodosAction) {
        when (action) {
            is TodosAction.OnTodoSwipe -> {
                when (action.direction) {
                    SwipeDirection.LEFT -> {
                        viewModelScope.launch {
                            taskRepository.deleteTask(action.task)
                            eventChannel.send(TodosEvent.ShowSnackbar(task = action.task))
                        }
                    }

                    SwipeDirection.RIGHT -> {
                        viewModelScope.launch {
                            val task = action.task
                            val updatedTask = task.copy(completed = !task.completed)
                            taskRepository.upsertTask(updatedTask)
                        }
                    }
                }
            }

            is TodosAction.OnUndoDelete -> {
                viewModelScope.launch {
                    when (val result = taskRepository.upsertTask(action.task)) {
                        is Result.Error -> {
                            eventChannel.send(TodosEvent.Error(error = result.error.asUiText()))
                        }

                        else -> Unit
                    }
                }
            }

            else -> Unit
        }
    }
}
