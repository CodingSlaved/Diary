package com.codingslaved.diary

import android.app.Application
import com.codingslaved.diary.data.container.DiaryContainer
import com.codingslaved.diary.data.container.DiaryContainerImpl

class DiaryApplication: Application() {
    lateinit var container: DiaryContainer

    override fun onCreate() {
        super.onCreate()
        container = DiaryContainerImpl(this)
    }
}