package sk.vmproject.todoapp.domain.model

data class Task(
    val id: String?,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val createdAt: String
)
