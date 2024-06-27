package sk.vmproject.todoapp.domain.remote

import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.domain.utils.DataError
import sk.vmproject.todoapp.domain.utils.Result

interface RemoteTaskDataSource {

    suspend fun getTask(): Result<List<Task>, DataError.Network>
}