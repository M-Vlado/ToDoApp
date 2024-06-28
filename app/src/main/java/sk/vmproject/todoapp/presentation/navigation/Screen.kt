package sk.vmproject.todoapp.presentation.navigation

sealed class Screen(val route: String) {

    data object TodosScreen : Screen(route = "todos_screen")
    data object TodoDetailScreen : Screen(route = "todo_detail_screen/{$TODO_ID}") {
        fun routeWithParams(todoId: Long): String {
            return "todo_detail_screen/$todoId"
        }
    }

    data object AddTodoScreen : Screen(route = "add_todo_screen")
}