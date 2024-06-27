package sk.vmproject.todoapp.data.remote

import io.ktor.client.HttpClient
import sk.vmproject.todoapp.data.remote.dto.TaskDto
import sk.vmproject.todoapp.data.remote.dto.toTask
import sk.vmproject.todoapp.domain.model.Task
import sk.vmproject.todoapp.domain.remote.RemoteTaskDataSource
import sk.vmproject.todoapp.domain.utils.DataError
import sk.vmproject.todoapp.domain.utils.Result
import sk.vmproject.todoapp.domain.utils.map

class RemoteTaskDataSourceImpl(
    private val client: HttpClient
) : RemoteTaskDataSource {
    override suspend fun getTask(): Result<List<Task>, DataError.Network> {
        return client.get<List<TaskDto>>(
            route = "/api/v1/tasks"
        ).map { taskDtos ->
            taskDtos.map { it.toTask() }
        }
    }

}