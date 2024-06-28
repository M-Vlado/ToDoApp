package sk.vmproject.todoapp.presentation.di

import android.content.Context.MODE_PRIVATE
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import sk.vmproject.todoapp.presentation.add_todo.AddTodoViewModel
import sk.vmproject.todoapp.presentation.todo_detail.TodoDetailViewModel
import sk.vmproject.todoapp.presentation.todos.TodosViewModel

val presentationModule = module {
    single {
        androidApplication().getSharedPreferences("default", MODE_PRIVATE)
    }
    viewModelOf(::TodosViewModel)
    viewModelOf(::TodoDetailViewModel)
    viewModelOf(::AddTodoViewModel)
}