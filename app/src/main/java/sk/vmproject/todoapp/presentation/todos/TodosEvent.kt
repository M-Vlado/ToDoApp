package sk.vmproject.todoapp.presentation.todos

import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.presentation.utils.UiText

sealed interface TodosEvent {
    data class Error(val error: UiText) : TodosEvent
    data class ShowSnackbar(val task: Task) : TodosEvent
}