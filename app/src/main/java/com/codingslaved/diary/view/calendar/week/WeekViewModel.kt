package com.codingslaved.diary.view.calendar.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingslaved.diary.utils.WhileUiSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


data class WeekUiState(
    val visibleDates : List<WeekViewModel.Date> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val focusDate: LocalDate = LocalDate.now(),
)

class WeekViewModel(): ViewModel() {
    private val today: LocalDate = LocalDate.now()
    private val _visibleDates: MutableStateFlow<List<WeekViewModel.Date>> = MutableStateFlow(emptyList())
    private val _selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(today)
    private val _focusDate: MutableStateFlow<LocalDate> = MutableStateFlow(today)

    val uiState: StateFlow<WeekUiState> = combine(
        _visibleDates,
        _selectedDate,
        _focusDate
    ) { visibleDates, selectedDate, focusDate ->
        WeekUiState(
            visibleDates = visibleDates,
            selectedDate = selectedDate,
            focusDate = focusDate,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = WeekUiState()
    )

    fun generateVisibleDates(focusDate: LocalDate) {
        val firstDayOfWeek = focusDate.with(DayOfWeek.MONDAY)
        val dates = (0..6).map { firstDayOfWeek.plusDays(it.toLong()) }
        _visibleDates.value = dates.map {
            WeekViewModel.Date(
                date = it,
                isSelected = it == _selectedDate.value,
                isToday = it == today
            )
        }
    }

    fun updateSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        generateVisibleDates(date)
    }

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }
}