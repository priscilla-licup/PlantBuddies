package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GardenProfileViewModel : ViewModel() {
    val plantName = MutableLiveData<String>()
    val plantDesc = MutableLiveData<String>()
}
