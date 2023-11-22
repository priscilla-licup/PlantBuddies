package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {


    private val _notesList = MutableLiveData<MutableList<Note>>().apply {
        value = mutableListOf()
    }
    val notesList: LiveData<MutableList<Note>> = _notesList

    fun addNewNote(note: Note) {
        val currentList = _notesList.value ?: mutableListOf()
        currentList.add(note)
        _notesList.value = currentList
    }

    fun updateNote(note: Note) {
        val currentList = _notesList.value ?: mutableListOf()
        val noteIndex = currentList.indexOfFirst { it.id == note.id }
        if (noteIndex != -1) {
            currentList[noteIndex] = note
            _notesList.value = currentList
        }
    }

    fun updateNoteImageUri(noteId: Int, imageUri: Uri) {
        val currentList = _notesList.value ?: mutableListOf()
        val noteIndex = currentList.indexOfFirst { it.id == noteId }
        if (noteIndex != -1) {
            val updatedNote = currentList[noteIndex].copy(imageUri = imageUri.toString())
            currentList[noteIndex] = updatedNote
            _notesList.value = currentList
        }
    }

    fun deleteNote(note: Note) {
        val currentList = _notesList.value ?: mutableListOf()
        currentList.remove(note)
        _notesList.value = currentList
    }

}