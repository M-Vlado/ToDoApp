package sk.vmproject.todoapp.data

import android.database.sqlite.SQLiteFullException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sk.vmproject.todoapp.data.locale.db.dao.TaskDao
import sk.vmproject.todoapp.data.locale.db.entity.toTask
import sk.vmproject.todoapp.data.locale.db.entity.toTaskEntity
import sk.vmproject.todoapp.data.remote.RemoteTaskDataSource
import sk.vmproject.todoapp.domain.TaskId
import sk.vmproject.todoapp.domain.TaskRepository
import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.domain.utils.DataError
import sk.vmproject.todoapp.domain.utils.EmptyResult
import sk.vmproject.todoapp.domain.utils.Result
import sk.vmproject.todoapp.domain.utils.asEmptyDataResult

class TaskRepositoryImpl(
    private val remoteTaskDataSource: RemoteTaskDataSource,
    private val taskDao: TaskDao,
    private val applicationScope: CoroutineScope,
) : TaskRepository {

    override suspend fun fetchAllTasks(): EmptyResult<DataError> {
        return when (val result = remoteTaskDataSource.getAllTasks()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    try {
                        val entities = result.data.map { it.toTaskEntity() }
                        taskDao.upsertAllTask(entities)
                        Result.Success(null).asEmptyDataResult()
                    } catch (e: SQLiteFullException) {
                        Result.Error(DataError.Local.DISK_FULL).asEmptyDataResult()
                    }
                }.await()
            }
        }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTask().map { tasksEntity ->
            tasksEntity.map { it.toTask() }
        }
    }

    override suspend fun getTaskById(taskId: Int): Task {
        return taskDao.getTaskById(taskId = taskId).toTask()
    }

    override suspend fun upsertTask(task: Task): Result<TaskId, DataError> {
        return try {
            val entity = task.toTaskEntity()
            val taskId = taskDao.upsertTask(taskEntity = entity)
            Result.Success(taskId)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteTask(task: Task) {
        val taskEntity = task.toTaskEntity()
        taskDao.deleteTask(taskEntity = taskEntity)
    }

}