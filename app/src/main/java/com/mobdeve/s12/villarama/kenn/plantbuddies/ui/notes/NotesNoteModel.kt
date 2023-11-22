package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val imageUri: String?, // URI of the selected image
    val toggleShovel: Boolean, // State of ToggleButton1
    val toggleWater: Boolean, // State of ToggleButton2
    val toggleSeeds: Boolean, // State of ToggleButton2
    val toggleInsect: Boolean, // State of ToggleButton2
    val toggleHarvest: Boolean // State of ToggleButton2
    // Add fields for additional toggle buttons as needed
    // Add other fields as necessary
)