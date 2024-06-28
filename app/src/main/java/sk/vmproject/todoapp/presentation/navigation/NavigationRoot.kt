package sk.vmproject.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import sk.vmproject.todoapp.presentation.add_todo.AddTodoScreenRoot
import sk.vmproject.todoapp.presentation.todo_detail.TodoDetailScreenRoot
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
                    navController.navigate(Screen.TodoDetailScreen.routeWithParams(todoId = it))
                },
                onAddTodoClick = {
                    navController.navigate(Screen.AddTodoScreen.route)
                }
            )
        }

        composable(
            route = Screen.TodoDetailScreen.route,
            arguments = listOf(
                navArgument(TODO_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            TodoDetailScreenRoot(
                onGoBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.AddTodoScreen.route,
        ) {
            AddTodoScreenRoot(
                onGoBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}