package com.kkdev.notable.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kkdev.notable.data.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Upsert
    suspend fun upsertNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY noteTitle ASC")
    fun getNotesOrderedByTitle(): Flow<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getNewNotes(): Flow<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getOldNotes(): Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): Flow<Notes>
}
//data access object for Notes
//dao defines how to interact with our database