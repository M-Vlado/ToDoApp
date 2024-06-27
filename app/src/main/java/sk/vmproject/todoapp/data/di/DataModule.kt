package sk.vmproject.todoapp.data.di

import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sk.vmproject.todoapp.TodoApp
import sk.vmproject.todoapp.data.TaskRepositoryImpl
import sk.vmproject.todoapp.data.locale.db.TaskDatabase
import sk.vmproject.todoapp.data.remote.HttpClientFactory
import sk.vmproject.todoapp.data.remote.RemoteTaskDataSource
import sk.vmproject.todoapp.data.remote.RemoteTaskDataSourceImpl
import sk.vmproject.todoapp.domain.TaskRepository

val dataModule = module {
    single {
        HttpClientFactory().build()
    }

    singleOf(::RemoteTaskDataSourceImpl).bind<RemoteTaskDataSource>()

    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java,
            "task_db"
        ).build()
    }

    single { get<TaskDatabase>().taskDao }

    singleOf(::TaskRepositoryImpl).bind<TaskRepository>()

    single<CoroutineScope> {
        (androidApplication() as TodoApp).applicationScope
    }
}