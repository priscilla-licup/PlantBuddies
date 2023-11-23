package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyApplication
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemRepository
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    val notesList: LiveData<List<Note>> = repository.allNotes.asLiveData()

//    fun addNewNote(note: Note) {viewModelScope.launch(Dispatchers.IO) {
//        Log.d("ViewModel", "Adding Note: $note")
//        db.notesDao().insert(note)
//    }
//    }

    fun addNewNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

//    fun updateNote(note: Note) {
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("ViewModel", "Updating Note: $note")
//            db.notesDao().update(note)
//        }
//    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

//    fun deleteNote(note: Note) {
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("ViewModel", "Deleting Note: $note")
//            db.notesDao().delete(note)
//        }
//    }

    fun deleteNote(note: Note) = viewModelScope.launch{
        repository.deleteNote(note)
    }
}

class NoteModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory
{
//    override fun <N : ViewModel> create(modelClass: Class<N>): N
//    {
//        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
//            return NotesViewModel(repository) as N
//
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}