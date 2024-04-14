package com.kkdev.notable.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kkdev.notable.data.daos.NotesDao
import com.kkdev.notable.data.model.Notes

@Database(
    entities = [Notes::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val dao: NotesDao
}
