package com.mobdeve.s12.villarama.kenn.plantbuddies

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.User
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.UserDao
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NotesDao
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.Note
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItem
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.TaskItemDao

@Database(entities = [TaskItem::class, Note::class, User::class], version = 1, exportSchema = false)
public abstract class PlantBuddyDatabase : RoomDatabase()
{
    abstract fun taskItemDao(): TaskItemDao
    abstract fun noteDao(): NotesDao
    abstract fun userDao(): UserDao

    companion object
    {
        @Volatile
        private var INSTANCE: PlantBuddyDatabase? = null

        fun getDatabase(context: Context): PlantBuddyDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantBuddyDatabase::class.java,
                    "plant_buddy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}