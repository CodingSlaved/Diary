package com.codingslaved.diary.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate

class SharedViewModel : ViewModel() {
    private val _data = MutableStateFlow<LocalDate?>(null)
    val data = _data.asSharedFlow()

    fun setData(date: LocalDate) {
        _data.value = date
    }
}