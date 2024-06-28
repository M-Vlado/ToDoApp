package sk.vmproject.todoapp.presentation.todo_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import sk.vmproject.todoapp.R
import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.presentation.utils.parseAndFormatDate
import sk.vmproject.todoapp.ui.theme.ToDoAppTheme
import sk.vmproject.todoapp.ui.theme.onErrorContainerDark
import sk.vmproject.todoapp.ui.theme.onPrimaryLight
import sk.vmproject.todoapp.ui.theme.primaryLight
import sk.vmproject.todoapp.ui.theme.secondaryContainerLight

@Composable
fun TodoDetailScreenRoot(
    viewModel: TodoDetailViewModel = koinViewModel(),
    onGoBackClick: () -> Unit
) {

    TodoDetailScreen(
        state = viewModel.todoDetailState,
        onAction = { action ->
            when (action) {
                TodoDetailAction.OnGoBack -> onGoBackClick()
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoDetailScreen(
    state: TodoDetailState,
    onAction: (TodoDetailAction) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.todo_detail)) },
                colors = TopAppBarColors(
                    containerColor = primaryLight,
                    scrolledContainerColor = primaryLight,
                    navigationIconContentColor = onPrimaryLight,
                    titleContentColor = onPrimaryLight,
                    actionIconContentColor = onPrimaryLight
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(TodoDetailAction.OnGoBack) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = stringResource(
                                R.string.go_back
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            state.todo?.let { task ->
                OutlinedCard(
                    elevation = CardDefaults.outlinedCardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (task.completed) Modifier.background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            secondaryContainerLight,
                                            onErrorContainerDark
                                        )
                                    )
                                ) else Modifier.background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                )
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = task.title,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.headlineSmall,
                            textDecoration = if (task.completed) TextDecoration.LineThrough else null,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp)
                        )
                        Icon(
                            painter = painterResource(id = if (task.completed) R.drawable.done_icon else R.drawable.alarm_clock_item),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                    task.description?.let { description ->
                        Text(
                            text = description,
                            textDecoration = if (task.completed) TextDecoration.LineThrough else null,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                    Text(
                        text = parseAndFormatDate(task.createdAt),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TodoDetailScreenPreview() {
    ToDoAppTheme {
        TodoDetailScreen(
            state = TodoDetailState(
                todo = Task(
                    id = 1L,
                    title = "Future Division Officer",
                    description = "Saepe consequuntur placeat fugiat aut deleniti ad provident repellendus sed. Voluptate officia provident. Labore iusto beatae explicabo quisquam molestiae cupiditate magni harum. A voluptatibus alias ratione consequatur. Vero enim assumenda modi impedit ad eius in nihil. Soluta laudantium animi porro dicta architecto facilis magni error.",
                    completed = true,
                    createdAt = "2024-06-11T13:44:20.647Z"
                )
            ),
            onAction = {}
        )
    }
}