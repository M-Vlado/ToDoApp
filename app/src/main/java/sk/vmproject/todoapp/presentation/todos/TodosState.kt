package sk.vmproject.todoapp.presentation.todos

import sk.vmproject.todoapp.domain.model.Task

data class TodosState(
    val todos: List<Task> = emptyList(),
    val isLoading: Boolean = false
)
