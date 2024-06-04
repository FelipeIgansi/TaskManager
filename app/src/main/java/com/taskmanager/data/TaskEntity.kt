package com.taskmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskEntity")
data class TaskEntity(
    @ColumnInfo @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo val title: String = "",
    @ColumnInfo val content: String = ""
)