package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}
