package sk.vmproject.todoapp.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sk.vmproject.todoapp.data.remote.HttpClientFactory
import sk.vmproject.todoapp.data.remote.RemoteTaskDataSourceImpl
import sk.vmproject.todoapp.domain.remote.RemoteTaskDataSource

val dataModule = module {
    single {
        HttpClientFactory().build()
    }

    singleOf(::RemoteTaskDataSourceImpl).bind<RemoteTaskDataSource>()
}