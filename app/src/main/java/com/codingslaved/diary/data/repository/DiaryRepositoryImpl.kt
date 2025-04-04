package com.codingslaved.diary.data.repository

import com.codingslaved.diary.data.dao.DiaryDao
import com.codingslaved.diary.data.model.Diary
import kotlinx.coroutines.flow.Flow

class DiaryRepositoryImpl(private val diaryDao: DiaryDao) : DiaryRepository {
    override fun getAll(): Flow<List<Diary>> = diaryDao.getAll()

    override suspend fun insert(diary: Diary) = diaryDao.insert(diary)

}