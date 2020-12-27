package com.codinginflow.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codinginflow.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao

    class Callback @Inject constructor( private val database : Provider<TaskDatabase>, @ApplicationScope private val applicationScope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("lavar los platos"))
                dao.insert(Task("ir a la lavandería", important = true))
                dao.insert(Task("ir al supermercado", completed = true))
                dao.insert(Task("Hacer deberes"))
                dao.insert(Task("asear al perro"))
                dao.insert(Task("comprar un microfono", completed = true))
            }
            //db operations
        }
    }
}