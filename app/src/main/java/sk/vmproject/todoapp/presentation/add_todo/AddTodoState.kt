package sk.vmproject.todoapp.presentation.add_todo

import sk.vmproject.todoapp.presentation.utils.UiText

data class AddTodoState(
    val title: String = "",
    val titleError: UiText? = null,
    val description: String? = null,
    val isSaving: Boolean = false,
    val showUnsavedDialog: Boolean = false
)
