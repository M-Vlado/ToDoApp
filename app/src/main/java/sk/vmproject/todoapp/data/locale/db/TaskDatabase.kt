package sk.vmproject.todoapp.data.locale.db

import androidx.room.Database
import androidx.room.RoomDatabase
import sk.vmproject.todoapp.data.locale.db.dao.TaskDao
import sk.vmproject.todoapp.data.locale.db.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class
    ],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao
}