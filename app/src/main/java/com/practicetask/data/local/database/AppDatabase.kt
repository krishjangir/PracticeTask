package com.practicetask.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practicetask.data.local.dao.ATMDao
import com.practicetask.data.model.ATMData

@Database(entities = [ATMData::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "PracticeTask-db"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
            }
            return INSTANCE as AppDatabase
        }
    }

    abstract fun atmDao(): ATMDao?
}
