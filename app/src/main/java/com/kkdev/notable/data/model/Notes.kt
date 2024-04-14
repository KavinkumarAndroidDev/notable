package com.kkdev.notable.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val noteTitle: String,
    val noteContent: String,
    val lastEdited: String
)
