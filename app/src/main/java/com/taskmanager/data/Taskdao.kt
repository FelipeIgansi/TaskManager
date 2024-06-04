package com.taskmanager.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Taskdao {
    @Insert
    fun insertAll ( vararg taskEntity: TaskEntity)

    @Update
    fun update (taskEntity: TaskEntity)

    @Delete
    fun delete (taskEntity: TaskEntity)

    @Query("SELECT * From taskEntity")
    fun getAll(): List<TaskEntity>
}