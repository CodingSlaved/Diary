package com.codingslaved.diary.data.container

import com.codingslaved.diary.data.repository.DiaryRepository
import android.content.Context
import com.codingslaved.diary.data.database.DiaryDatabase
import com.codingslaved.diary.data.repository.DiaryRepositoryImpl

interface DiaryContainer {
    val repository: DiaryRepository
}

class DiaryContainerImpl(private val context: Context) : DiaryContainer {

    override val repository: DiaryRepository by lazy {
        DiaryRepositoryImpl(DiaryDatabase.getDatabase(context).diaryDao())
    }
}