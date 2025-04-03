package com.codingslaved.diary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codingslaved.diary.data.model.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY id DESC")
    fun getAll(): Flow<List<Diary>>

    @Insert
    suspend fun insert(diary: Diary)

}