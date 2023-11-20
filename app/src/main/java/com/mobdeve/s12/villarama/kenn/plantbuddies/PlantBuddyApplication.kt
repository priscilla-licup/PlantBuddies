package com.mobdeve.s12.villarama.kenn.plantbuddies

import android.app.Application
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemRepository

class PlantBuddyApplication : Application()
{
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
}