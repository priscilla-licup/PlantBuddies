package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllNotes(): Flow<List<Note>> // kenn has Flow instead of LiveData
    // LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}
