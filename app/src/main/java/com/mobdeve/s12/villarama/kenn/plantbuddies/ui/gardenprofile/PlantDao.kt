package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant_item_table ORDER BY id ASC")
    fun getAllPlantItems(): Flow<List<PlantItem>> // kenn has Flow instead of LiveData
    // LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plant: PlantItem)

    @Update
    suspend fun update(plant: PlantItem)

    @Delete
    suspend fun delete(plant: PlantItem)
}