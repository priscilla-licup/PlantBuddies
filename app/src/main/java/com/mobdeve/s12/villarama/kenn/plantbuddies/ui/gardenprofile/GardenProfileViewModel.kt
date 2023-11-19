package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GardenProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is garden profile fragment"
    }
    val text: LiveData<String> = _text
}