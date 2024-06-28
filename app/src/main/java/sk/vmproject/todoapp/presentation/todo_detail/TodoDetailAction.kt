package sk.vmproject.todoapp.presentation.todo_detail

sealed interface TodoDetailAction {
    data object OnGoBack : TodoDetailAction
}