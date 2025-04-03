package com.codingslaved.diary.viewmodel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.codingslaved.diary.DiaryApplication

object DiaryProvider {
    val Factory = viewModelFactory {
        initializer {
            DiaryEntryViewModel(diaryApplication().container.repository)
        }
    }
}

fun CreationExtras.diaryApplication(): DiaryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DiaryApplication)