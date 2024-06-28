package sk.vmproject.todoapp.presentation.todos.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.vmproject.todoapp.R
import sk.vmproject.todoapp.ui.theme.ToDoAppTheme

@Composable
fun TodoCardItem(
    todoId: Long,
    title: String,
    description: String?,
    isCompleted: Boolean,
    onClick: (Long) -> Unit
) {

    OutlinedCard(
        onClick = { onClick(todoId) },
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                )
                Icon(
                    painter = painterResource(id = if (isCompleted) R.drawable.done_icon else R.drawable.alarm_clock_item),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
            description?.let {
                Text(
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TodoCardItemPreviewLightMode() {
    ToDoAppTheme {
        TodoCardItem(
            todoId = 1L,
            title = "Future Division Officer Future Division Officer Future Division Officer",
            description = "Saepe consequuntur placeat fugiat aut deleniti ad provident repellendus sed. Voluptate officia provident. Labore iusto beatae explicabo quisquam molestiae cupiditate magni harum. A voluptatibus alias ratione consequatur. Vero enim assumenda modi impedit ad eius in nihil. Soluta laudantium animi porro dicta architecto facilis magni error.",
            isCompleted = false,
        ) {

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TodoCardItemPreviewDarkMode() {
    ToDoAppTheme {
        TodoCardItem(
            todoId = 1L,
            title = "Future Division Officer",
            description = "Saepe consequuntur placeat fugiat aut deleniti ad provident repellendus sed. Voluptate officia provident. Labore iusto beatae explicabo quisquam molestiae cupiditate magni harum. A voluptatibus alias ratione consequatur. Vero enim assumenda modi impedit ad eius in nihil. Soluta laudantium animi porro dicta architecto facilis magni error.",
            isCompleted = true,
        ) {

        }
    }
}