package sk.vmproject.todoapp.domain

import kotlinx.coroutines.flow.Flow
import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.domain.utils.DataError
import sk.vmproject.todoapp.domain.utils.EmptyResult
import sk.vmproject.todoapp.domain.utils.Result

typealias TaskId = Long

interface TaskRepository {

    suspend fun fetchAllTasks(): EmptyResult<DataError>

    fun getAllTasks(): Flow<List<Task>>

    suspend fun getTaskById(taskId: Int): Task

    suspend fun upsertTask(task: Task): Result<TaskId, DataError>

    suspend fun deleteTask(task: Task)
}