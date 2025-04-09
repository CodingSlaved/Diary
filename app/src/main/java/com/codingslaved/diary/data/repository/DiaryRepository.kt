package com.codingslaved.diary.data.repository

import com.codingslaved.diary.data.model.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getDiary(date: String): Flow<Diary>
    suspend fun insert(diary: Diary)
    suspend fun update(diary: Diary)

}