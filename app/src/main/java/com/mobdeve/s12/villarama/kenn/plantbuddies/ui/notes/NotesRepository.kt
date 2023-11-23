package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItem
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemDao
import kotlinx.coroutines.flow.Flow

class NotesRepository (private val noteDao: NotesDao)
{
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insertNote(note: Note)
    {
        noteDao.insert(note)
    }

    @WorkerThread
    suspend fun updateNote(note: Note)
    {
        noteDao.update(note)
    }
    suspend fun deleteTaskItem(note: Note)
    {
        noteDao.delete(note)
    }
}