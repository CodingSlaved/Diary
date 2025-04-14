package com.codingslaved.diary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.codingslaved.diary.data.model.Diary
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary WHERE date = :date")
    fun getDiary(date: LocalDate): Flow<Diary>

    @Insert
    suspend fun insert(diary: Diary)

    @Update
    suspend fun update(diary: Diary)

}