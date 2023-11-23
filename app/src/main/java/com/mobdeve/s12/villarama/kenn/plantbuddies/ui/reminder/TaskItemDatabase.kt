package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.NotesDao
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.notes.Note

@Database(entities = [TaskItem::class, Note::class], version = 1, exportSchema = false)
public abstract class TaskItemDatabase : RoomDatabase()
{
    abstract fun taskItemDao(): TaskItemDao
    abstract fun noteDao(): NotesDao

    companion object
    {
        @Volatile
        private var INSTANCE: TaskItemDatabase? = null

        fun getDatabase(context: Context): TaskItemDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemDatabase::class.java,
                    "task_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}