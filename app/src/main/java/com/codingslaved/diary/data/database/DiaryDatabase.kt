package com.codingslaved.diary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingslaved.diary.data.converter.Converters
import com.codingslaved.diary.data.dao.DiaryDao
import com.codingslaved.diary.data.model.Diary

@Database(entities = [Diary::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getDatabase(
            context: Context,
        ): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}