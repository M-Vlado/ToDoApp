package sk.vmproject.todoapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import sk.vmproject.todoapp.data.di.dataModule
import sk.vmproject.todoapp.presentation.di.presentationModule

class TodoApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TodoApp)
            modules(
                dataModule,
                presentationModule
            )
        }
    }
}