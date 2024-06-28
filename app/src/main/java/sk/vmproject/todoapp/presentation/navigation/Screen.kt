package sk.vmproject.todoapp.presentation.navigation

sealed class Screen(val route: String) {

    data object TodosScreen : Screen(route = "todos_screen")
    data object DetailTodoScreen : Screen(route = "detail_todo_screen/{$TODO_ID}") {
        fun routeWithParams(todoId: Long): String {
            return "detail_todo_screen/$todoId"
        }
    }

    data object AddEditTodoScreen : Screen(route = "add_edit_todo_screen?$TODO_ID={$TODO_ID}") {
        fun routeWithParams(todoId: Long?): String {
            return "add_edit_todo_screen?$TODO_ID=$todoId"
        }
    }
}