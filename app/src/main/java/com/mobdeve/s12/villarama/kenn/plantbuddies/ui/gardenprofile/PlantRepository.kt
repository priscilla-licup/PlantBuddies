package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PlantRepository(private val plantDao: PlantDao) {

    val allPlantItems: Flow<List<PlantItem>> = plantDao.getAllPlantItems()

    @WorkerThread
    suspend fun insertPlant(plant: PlantItem)
    {
        plantDao.insert(plant)
    }

    @WorkerThread
    suspend fun updatePlant(plant: PlantItem)
    {
        plantDao.update(plant)
    }
    suspend fun deletePlant(plant: PlantItem)
    {
        plantDao.delete(plant)
    }
}