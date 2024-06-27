package sk.vmproject.todoapp.data.locale.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.vmproject.todoapp.domain.model.Task

@Entity(tableName = "task_entity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String?,
    val completed: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: String
)

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        completed = this.completed,
        createdAt = this.createdAt
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id ?: 0,
        title = title,
        description = description,
        completed = completed,
        createdAt = createdAt
    )
}
