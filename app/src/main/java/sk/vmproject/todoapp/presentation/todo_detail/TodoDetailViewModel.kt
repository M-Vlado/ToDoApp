package sk.vmproject.todoapp.presentation.todo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.vmproject.todoapp.domain.TaskRepository
import sk.vmproject.todoapp.presentation.navigation.TODO_ID

class TodoDetailViewModel(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId = savedStateHandle.get<Long>(TODO_ID)

    var todoDetailState by mutableStateOf(TodoDetailState())
        private set

    init {
        todoId?.let {
            viewModelScope.launch {
                val todo = taskRepository.getTaskById(taskId = todoId)
                todoDetailState = todoDetailState.copy(todo = todo)
            }
        }
    }
}