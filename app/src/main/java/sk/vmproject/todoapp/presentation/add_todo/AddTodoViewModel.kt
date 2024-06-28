package sk.vmproject.todoapp.presentation.add_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sk.vmproject.todoapp.R
import sk.vmproject.todoapp.domain.TaskRepository
import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.domain.utils.Result
import sk.vmproject.todoapp.domain.utils.asUiText
import sk.vmproject.todoapp.presentation.utils.UiText
import sk.vmproject.todoapp.presentation.utils.createFormatedDate
import java.util.Date

class AddTodoViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    var addTodoState by mutableStateOf(AddTodoState())
        private set

    private val eventChannel = Channel<AddTodoEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAddTodoAction(action: AddTodoAction) {
        when (action) {
            is AddTodoAction.OnTitleChanged -> {
                addTodoState = addTodoState.copy(title = action.title)
            }

            is AddTodoAction.OnDescriptionChanged -> {
                addTodoState = addTodoState.copy(description = action.description)
            }

            AddTodoAction.OnSaveTodo -> {
                addTodoState = addTodoState.copy(showUnsavedDialog = false)
                if (addTodoState.title.isNotBlank()) {
                    viewModelScope.launch {
                        val taskToSave = Task(
                            id = null,
                            title = addTodoState.title.trim(),
                            description = addTodoState.description?.trim(),
                            completed = false,
                            createdAt = createFormatedDate(Date())
                        )
                        when (val result = taskRepository.upsertTask(taskToSave)) {
                            is Result.Error -> {
                                eventChannel.send(AddTodoEvent.Error(error = result.error.asUiText()))
                            }

                            is Result.Success -> {
                                eventChannel.send(AddTodoEvent.TodoAdded)
                            }
                        }
                    }
                } else {
                    addTodoState =
                        addTodoState.copy(titleError = UiText.StringResource(R.string.error_title_required))
                }
            }

            else -> Unit
        }
    }

    fun showUnsavedDialog(show: Boolean) {
        addTodoState = addTodoState.copy(showUnsavedDialog = show)
    }
}