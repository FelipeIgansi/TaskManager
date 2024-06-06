package com.taskmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskEntity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "content") var content: String = ""
)