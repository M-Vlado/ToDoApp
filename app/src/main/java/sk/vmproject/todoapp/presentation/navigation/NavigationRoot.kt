package sk.vmproject.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import sk.vmproject.todoapp.presentation.todos.TodosScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = TODO_GRAPH
    ) {
        todoGraph(navController = navController)
    }
}

private fun NavGraphBuilder.todoGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.TodosScreen.route,
        route = TODO_GRAPH
    ) {
        composable(
            route = Screen.TodosScreen.route
        ) {
            TodosScreenRoot(
                onTodoClick = {
                    navController.navigate(Screen.DetailTodoScreen.routeWithParams(todoId = it))
                },
                onAddTodoClick = {
                    navController.navigate(Screen.AddEditTodoScreen.routeWithParams(todoId = null))
                }
            )
        }

        composable(
            route = Screen.DetailTodoScreen.route,
            arguments = listOf(
                navArgument(TODO_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {

        }

        composable(
            route = Screen.AddEditTodoScreen.route,
            arguments = listOf(
                navArgument(TODO_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {

        }
    }
}