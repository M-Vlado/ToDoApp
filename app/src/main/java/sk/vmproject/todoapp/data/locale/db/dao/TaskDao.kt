package sk.vmproject.todoapp.data.locale.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import sk.vmproject.todoapp.data.locale.db.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_entity")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_entity WHERE id= :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Int): TaskEntity

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity): Long

    @Upsert
    suspend fun upsertAllTask(tasksEntity: List<TaskEntity>): List<Long>

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)
}