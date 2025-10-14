package com.studybuddy.app.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.studybuddy.app.reminders.ReminderEntity

@Database(entities = [NoteEntity::class, ReminderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "studybuddy.db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}

