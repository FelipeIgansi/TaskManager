package com.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taskmanager.base.Constants

@Database(entities = [TaskEntity::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    abstract  fun taskdao() : Taskdao

    companion object{
        @Volatile
        private var INSTANCE : TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return  tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    Constants.DATABASE.DATABASENAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}