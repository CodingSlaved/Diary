package com.codingslaved.diary.data.repository

import com.codingslaved.diary.data.model.Diary
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DiaryRepository {
    fun getDiary(date: LocalDate): Flow<Diary>
    suspend fun insert(diary: Diary)
    suspend fun update(diary: Diary)

}