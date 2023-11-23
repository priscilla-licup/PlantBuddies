package com.mobdeve.s12.villarama.kenn.plantbuddies

import android.app.Application
import androidx.room.Room
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.AppDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemRepository

class PlantBuddyApplication : Application()
{
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }

    // NOT SURE ----------------
//    val noteDB = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java, "plant-buddies-database"
//    ).build()

//    val noteDB: AppDatabase by lazy {
//        Room.databaseBuilder(
//            this,
//            AppDatabase::class.java,
//            "plant-buddies-database"
//        ).build()
//    }

    lateinit var noteDB: AppDatabase

    override fun onCreate() {
        super.onCreate()
        noteDB = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "plant-buddies-database"
        ).build()
    }

}