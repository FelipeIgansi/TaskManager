package com.taskmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taskmanager.base.Constants

@Entity(tableName = "taskEntity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = Constants.DATABASE.TITLECOLUMN) var title: String = "",
    @ColumnInfo(name = Constants.DATABASE.CONTENTCOLUMN) var content: String = ""
)