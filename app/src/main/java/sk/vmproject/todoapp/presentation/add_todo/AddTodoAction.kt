package sk.vmproject.todoapp.presentation.add_todo

sealed interface AddTodoAction {

    data object OnSaveTodo : AddTodoAction
    data object OnGoBack : AddTodoAction
    data object OnDiscard : AddTodoAction
    data object OnDismiss : AddTodoAction
    data class OnTitleChanged(val title: String) : AddTodoAction
    data class OnDescriptionChanged(val description: String) : AddTodoAction
}