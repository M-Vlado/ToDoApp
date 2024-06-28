package sk.vmproject.todoapp.presentation.add_todo

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import sk.vmproject.todoapp.R
import sk.vmproject.todoapp.presentation.utils.ObserveAsEvents
import sk.vmproject.todoapp.ui.theme.ToDoAppTheme
import sk.vmproject.todoapp.ui.theme.onPrimaryLight
import sk.vmproject.todoapp.ui.theme.primaryLight

@Composable
fun AddTodoScreenRoot(
    viewModel: AddTodoViewModel = koinViewModel(),
    onGoBackClick: () -> Unit
) {
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is AddTodoEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            AddTodoEvent.TodoAdded -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.saved_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                onGoBackClick()
            }
        }
    }

    AddTodoScreen(
        state = viewModel.addTodoState,
        onAction = { action ->
            when (action) {
                AddTodoAction.OnDiscard -> {
                    viewModel.showUnsavedDialog(show = false)
                    onGoBackClick()
                }

                AddTodoAction.OnGoBack -> {
                    if (viewModel.addTodoState.title.isNotEmpty()
                        || viewModel.addTodoState.description?.isNotEmpty() == true
                    ) {
                        viewModel.showUnsavedDialog(show = true)
                    } else {
                        onGoBackClick()
                    }
                }

                AddTodoAction.OnDismiss -> {
                    viewModel.showUnsavedDialog(show = false)
                }

                else -> viewModel.onAddTodoAction(action)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTodoScreen(
    state: AddTodoState,
    onAction: (AddTodoAction) -> Unit
) {

    BackHandler {
        onAction(AddTodoAction.OnGoBack)
    }

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
                        onClick = { onAction(AddTodoAction.OnGoBack) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = stringResource(
                                R.string.go_back
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onAction(AddTodoAction.OnSaveTodo)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.save_icon),
                            contentDescription = stringResource(
                                R.string.save
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (state.showUnsavedDialog) {
            AlertDialog(
                title = {
                    Text(text = stringResource(R.string.unsaved_changes))
                },
                text = {
                    Text(text = stringResource(R.string.do_you_want_to_save_or_discard))
                },
                onDismissRequest = { onAction(AddTodoAction.OnDismiss) },
                confirmButton = {
                    TextButton(onClick = { onAction(AddTodoAction.OnSaveTodo) }) {
                        Text(
                            text = stringResource(id = R.string.save),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onAction(AddTodoAction.OnDiscard) }) {
                        Text(
                            text = stringResource(R.string.discard),
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.title,
                label = {
                    Text(text = stringResource(R.string.title))
                },
                onValueChange = {
                    onAction(AddTodoAction.OnTitleChanged(title = it))
                },
                supportingText = {
                    state.titleError?.let {
                        Text(text = it.asString())
                    }
                },
                isError = state.titleError != null,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.description ?: "",
                label = {
                    Text(text = stringResource(R.string.description))
                },
                onValueChange = {
                    onAction(AddTodoAction.OnDescriptionChanged(description = it))
                },
                supportingText = {
                    Text(text = stringResource(R.string.optional))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            )
        }
    }
}

@Preview
@Composable
private fun AddTodoScreenPreview() {
    ToDoAppTheme {
        AddTodoScreen(
            state = AddTodoState(),
            onAction = {}
        )
    }
}