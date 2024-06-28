package sk.vmproject.todoapp.presentation.todos

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import sk.vmproject.todoapp.R
import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.presentation.todos.components.SwipeItemContainer
import sk.vmproject.todoapp.presentation.todos.components.TodoCardItem
import sk.vmproject.todoapp.presentation.utils.ObserveAsEvents
import sk.vmproject.todoapp.ui.theme.ToDoAppTheme

@Composable
fun TodosScreenRoot(
    viewModel: TodosViewModel = koinViewModel(),
    onTodoClick: (todoId: Long) -> Unit,
    onAddTodoClick: () -> Unit
) {

    TodosScreen(
        state = viewModel.todosState,
        events = viewModel.events,
        onAction = { action ->
            when (action) {
                is TodosAction.OnTodoClick -> {
                    onTodoClick(action.todoId)
                }

                is TodosAction.OnAddTodoClick -> {
                    onAddTodoClick()
                }

                else -> {
                    viewModel.onTodosAction(action)
                }
            }
        }
    )
}

@Composable
private fun TodosScreen(
    state: TodosState,
    events: Flow<TodosEvent>,
    onAction: (TodosAction) -> Unit
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ObserveAsEvents(flow = events) { event ->
        when (event) {
            is TodosEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            is TodosEvent.ShowSnackbar -> {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        String.format(
                            context.getString(R.string.todo_was_deleted),
                            event.task.title
                        ),
                        actionLabel = context.getString(R.string.undo),
                        withDismissAction = true,
                        duration = SnackbarDuration.Long
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> Unit
                        SnackbarResult.ActionPerformed -> {
                            onAction(TodosAction.OnUndoDelete(task = event.task))
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(TodosAction.OnAddTodoClick) },
                modifier = Modifier
                    .padding(bottom = 40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = stringResource(
                        id = R.string.add_new_todo
                    )
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (!state.isLoading && state.todos.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_save_todos),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = state.todos,
                    key = {
                        it.id!!
                    }
                ) { task ->
                    SwipeItemContainer(
                        item = task,
                        onSwipeLeft = {
                            onAction(
                                TodosAction.OnTodoSwipe(
                                    direction = SwipeDirection.LEFT,
                                    task = it
                                )
                            )
                        },
                        onSwipeRight = {
                            onAction(
                                TodosAction.OnTodoSwipe(
                                    direction = SwipeDirection.RIGHT,
                                    task = it
                                )
                            )
                        },
                        onSwipeRightIcon = if (task.completed) R.drawable.alarm_clock_item else R.drawable.done_icon,
                        onSwipeLeftIcon = R.drawable.delete_icon,
                    ) {
                        TodoCardItem(
                            todoId = it.id!!,
                            title = it.title,
                            description = it.description,
                            isCompleted = it.completed,
                            onClick = { todoId ->
                                onAction(TodosAction.OnTodoClick(todoId = todoId))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TodosScreenPreview() {
    ToDoAppTheme {
        TodosScreen(
            state = TodosState(
                todos = listOf(
                    Task(
                        id = 1L,
                        title = "Future Division Officer",
                        description = "Saepe consequuntur placeat fugiat aut deleniti ad provident repellendus sed. Voluptate officia provident. Labore iusto beatae explicabo quisquam molestiae cupiditate magni harum. A voluptatibus alias ratione consequatur. Vero enim assumenda modi impedit ad eius in nihil. Soluta laudantium animi porro dicta architecto facilis magni error.",
                        completed = false,
                        createdAt = "2024-06-11T13:44:20.647Z"
                    )
                )
            ),
            events = flow { },
            onAction = {}
        )
    }
}