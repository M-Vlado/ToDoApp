package sk.vmproject.todoapp.data.remote.dto

import kotlinx.serialization.Serializable
import sk.vmproject.todoapp.domain.model.Task

@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val createdAt: String
)


fun TaskDto.toTask(): Task {
    return Task(
        id = this.id.toLongOrNull(),
        title = this.title,
        description = this.description,
        completed = this.completed,
        createdAt = this.createdAt
    )
}