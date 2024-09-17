package com.taskmanager.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Taskdao {
    @Insert
    suspend fun insertAll ( vararg taskEntity: TaskEntity)

    @Update
    suspend fun update (taskEntity: TaskEntity)

    @Delete
    suspend fun delete (taskEntity: TaskEntity)

    @Query("SELECT * From taskEntity where fkIDUser = :fkIDUser")
    suspend fun getAll(fkIDUser: String?): MutableList<TaskEntity>

    @Query("SELECT * From taskEntity where id like(:id)")
    suspend fun getByID(id: Long): TaskEntity
}