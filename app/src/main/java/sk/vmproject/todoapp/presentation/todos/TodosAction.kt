package sk.vmproject.todoapp.presentation.todos

import sk.vmproject.todoapp.domain.model.Task

sealed interface TodosAction {
    data class OnTodoClick(val todoId: Long) : TodosAction
    data class OnTodoSwipe(val direction: SwipeDirection, val task: Task) : TodosAction
    data class OnUndoDelete(val task: Task) : TodosAction
    data object OnAddTodoClick : TodosAction
}

enum class SwipeDirection {
    LEFT, RIGHT
}