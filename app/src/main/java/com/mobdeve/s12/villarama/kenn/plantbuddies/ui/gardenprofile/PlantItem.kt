package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_item_table")
class PlantItem (
    @ColumnInfo(name = "plant_name") var plantName: String,
    @ColumnInfo(name = "plant_desc") var plantDesc: String,
    @ColumnInfo(name = "plant_imageUri") var imageUri: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

