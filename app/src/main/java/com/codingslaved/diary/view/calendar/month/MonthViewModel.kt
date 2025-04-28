package com.codingslaved.diary.view.calendar.month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingslaved.diary.utils.WhileUiSubscribed
import com.codingslaved.diary.view.calendar.week.WeekUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.YearMonth

data class MonthUiState (
    val selectedDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = YearMonth.now(),
    val visibleDates : List<MonthViewModel.Date> = emptyList()
)

class MonthViewModel(
): ViewModel() {
    val today: LocalDate = LocalDate.now()
    private val _visibleDates: MutableStateFlow<List<MonthViewModel.Date>> = MutableStateFlow(emptyList())
    private val _currentMonth: MutableStateFlow<YearMonth> = MutableStateFlow(YearMonth.from(today))
    private val _selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(today)

    val uiState: StateFlow<MonthUiState> = combine(
        _visibleDates,
        _currentMonth,
        _selectedDate,
    ) { visibleDates, currentMonth, selectedDate ->
        MonthUiState(
            visibleDates = visibleDates,
            currentMonth = currentMonth,
            selectedDate = selectedDate
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = MonthUiState()
    )

    fun updateSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        _currentMonth.value = YearMonth.from(date)
        generateVisibleDates(_currentMonth.value)
    }

    fun updateCurrentMonth(month: YearMonth) {
        _currentMonth.value = month
        generateVisibleDates(month)
    }

    private fun generateVisibleDates(currentMonth: YearMonth) {
        val firstOfMonth = currentMonth.atDay(1)
        val firstDayOfWeek = firstOfMonth.dayOfWeek.value % 7
        val daysInMonth = currentMonth.lengthOfMonth()
        val prevMonth = currentMonth.minusMonths(1)
        val daysInPrevMonth = prevMonth.lengthOfMonth()

        val dates = ArrayList<LocalDate>(42)
        for (i in 0..41) {
            when {
                i < firstDayOfWeek -> dates.add(prevMonth.atDay(daysInPrevMonth - firstDayOfWeek + i + 1))
                i < daysInMonth + firstDayOfWeek -> dates.add(firstOfMonth.plusDays((i - firstDayOfWeek).toLong()))
                i < 35 -> dates.add(currentMonth.plusMonths(1).atDay(i - daysInMonth - firstDayOfWeek + 1))
                else -> break
            }
        }
        _currentMonth.value = currentMonth
        _visibleDates.value = dates.map {
            MonthViewModel.Date(
                date = it,
                isCurrentMonth = it.month == currentMonth.month,
                isSelected = it == _selectedDate.value,
                isToday = it == today
            )
        }
    }


    data class Date(
        val date: LocalDate,
        val isCurrentMonth: Boolean,
        val isSelected: Boolean,
        val isToday: Boolean
    )
}