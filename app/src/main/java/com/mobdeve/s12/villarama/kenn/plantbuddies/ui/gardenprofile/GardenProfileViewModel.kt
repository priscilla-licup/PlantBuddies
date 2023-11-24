package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.Note
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NotesRepository
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NotesViewModel
import kotlinx.coroutines.launch

class GardenProfileViewModel(private val repository: PlantRepository) : ViewModel() {

    val plantList: LiveData<List<PlantItem>> = repository.allPlantItems.asLiveData()

    fun addNewPlant(plant: PlantItem) = viewModelScope.launch {
        repository.insertPlant(plant)
    }

    fun updatePlant(plant: PlantItem) = viewModelScope.launch {
        repository.updatePlant(plant)
    }
    fun deletePlant(plant: PlantItem) = viewModelScope.launch{
        repository.deletePlant(plant)
    }

}

class PlantItemModelFactory(private val repository: PlantRepository) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}