package com.codingslaved.diary.view.calendar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingslaved.diary.utils.WhileUiSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

enum class Mode {
    MONTH,
    WEEK
}

object ModeHeight {
    val Month = 340.dp
    val Week = 150.dp
}

fun calculateHeight(mode: Mode): Dp {
    return when (mode) {
        Mode.MONTH -> ModeHeight.Month
        Mode.WEEK -> ModeHeight.Week
    }
}

data class CalendarUiState(
    val currentMode: Mode = Mode.MONTH,
    val selectDate : LocalDate = LocalDate.now()
)

class CalendarViewModel(): ViewModel() {
    private val today = LocalDate.now()
    private val _currentMode: MutableStateFlow<Mode> = MutableStateFlow(Mode.MONTH)
    private val _selectDate: MutableStateFlow<LocalDate> = MutableStateFlow(today)
    val uiState: StateFlow<CalendarUiState> = combine(
        _currentMode, _selectDate,
    ) { currentMode,  selectDate ->
        CalendarUiState(
            currentMode = currentMode,
            selectDate = selectDate,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = CalendarUiState()
    )

    fun toggleMode() {
        _currentMode.value = if (_currentMode.value == Mode.MONTH) Mode.WEEK else Mode.MONTH
    }

    fun setSelectDate(date: LocalDate) {
        _selectDate.value = date
    }
}