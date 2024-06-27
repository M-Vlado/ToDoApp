package sk.vmproject.todoapp.data.remote

import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.domain.utils.DataError
import sk.vmproject.todoapp.domain.utils.Result

interface RemoteTaskDataSource {

    suspend fun getAllTasks(): Result<List<Task>, DataError.Network>
}