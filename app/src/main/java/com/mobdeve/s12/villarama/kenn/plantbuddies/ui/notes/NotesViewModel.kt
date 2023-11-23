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

//    private val db: AppDatabase = getApplication<PlantBuddyApplication>().database

//    private val _notesList = MutableLiveData<MutableList<Note>>().apply {
//        value = mutableListOf()
//    }
//    val notesList: LiveData<MutableList<Note>> = _notesList

    val notesList: LiveData<List<Note>> = db.notesDao().getAllNotes()

    fun addNewNote(note: Note) {
//        val currentList = _notesList.value ?: mutableListOf()
//        currentList.add(note)
//        _notesList.value = currentList

        viewModelScope.launch(Dispatchers.IO) {
            db.notesDao().insert(note)
        }

        Log.d("ViewModel", "Adding Note: $note")
    }

    fun updateNote(note: Note) {
//        val currentList = _notesList.value ?: mutableListOf()
//        val noteIndex = currentList.indexOfFirst { it.id == note.id }
//        if (noteIndex != -1) {
//            currentList[noteIndex] = note
//            _notesList.value = currentList
//        }

        viewModelScope.launch(Dispatchers.IO) {
            db.notesDao().update(note)
        }
        Log.d("ViewModel", "Updating Note: $note")
    }

    fun deleteNote(note: Note) {
//        val currentList = _notesList.value ?: mutableListOf()
//        currentList.remove(note)
//        _notesList.value = currentList

        viewModelScope.launch(Dispatchers.IO) {
            db.notesDao().delete(note)
        }
        Log.d("ViewModel", "Deleting Note: $note")
    }

}