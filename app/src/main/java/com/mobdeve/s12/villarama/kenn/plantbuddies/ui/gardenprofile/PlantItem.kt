package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_item_table")
class PlantItem (
    @ColumnInfo(name = "plant_name") var plant_name: String,
    @ColumnInfo(name = "plant_desc") var plant_desc: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)



