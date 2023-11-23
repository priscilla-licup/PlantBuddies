package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

//data class Note(
//    val id: Int,
//    val title: String,
//    val content: String,
//    val date: String,
//    val imageUri: String?, // URI of the selected image
//    val toggleShovel: Boolean, // State of ToggleButton1
//    val toggleWater: Boolean, // State of ToggleButton2
//    val toggleSeeds: Boolean, // State of ToggleButton2
//    val toggleInsect: Boolean, // State of ToggleButton2
//    val toggleHarvest: Boolean // State of ToggleButton2
//    // Add fields for additional toggle buttons as needed
//    // Add other fields as necessary
//)

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "imageUri") var imageUri: String?,
    @ColumnInfo(name = "toggleShovel") var toggleShovel: Boolean,
    @ColumnInfo(name = "toggleWater") var toggleWater: Boolean,
    @ColumnInfo(name = "toggleSeeds") var toggleSeeds: Boolean,
    @ColumnInfo(name = "toggleInsect") var toggleInsect: Boolean,
    @ColumnInfo(name = "toggleHarvest") var toggleHarvest: Boolean,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

// kenn has other functions here

//class Note(
//    @ColumnInfo(name = "name") var name: String,
//    @ColumnInfo(name = "desc") var desc: String,
//    @ColumnInfo(name = "dueTimeString") var dueTimeString: String?,
//    @ColumnInfo(name = "completedDateString") var completedDateString: String?,
//    @PrimaryKey(autoGenerate = true) var id: Int = 0
//)
