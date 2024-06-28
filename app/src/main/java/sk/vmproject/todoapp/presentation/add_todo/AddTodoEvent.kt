package sk.vmproject.todoapp.presentation.add_todo

import sk.vmproject.todoapp.presentation.utils.UiText

sealed interface AddTodoEvent {
    data class Error(val error: UiText) : AddTodoEvent
    data object TodoAdded : AddTodoEvent
}