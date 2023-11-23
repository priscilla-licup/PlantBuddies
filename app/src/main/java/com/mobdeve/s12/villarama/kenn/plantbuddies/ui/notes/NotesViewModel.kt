package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val db: AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "plant-buddies-database"
    ).build()

    val notesList: LiveData<List<Note>> = db.notesDao().getAllNotes()

    fun addNewNote(note: Note) {viewModelScope.launch(Dispatchers.IO) {
        Log.d("ViewModel", "Adding Note: $note")
        db.notesDao().insert(note)
    }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModel", "Updating Note: $note")
            db.notesDao().update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModel", "Deleting Note: $note")
            db.notesDao().delete(note)
        }

    }

}