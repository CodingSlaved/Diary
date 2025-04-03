package com.codingslaved.diary.data.repository

import com.codingslaved.diary.data.model.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getAll(): Flow<List<Diary>>
    suspend fun insert(diary: Diary)
}