package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

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
}