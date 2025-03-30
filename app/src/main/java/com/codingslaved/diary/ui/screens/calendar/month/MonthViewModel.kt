package com.codingslaved.diary.ui.screens.calendar.month

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

data class MonthViewModel(
    val currentMonth: YearMonth,
    val selectedDate: Date,
    val visibleDates: List<Date>
) {

    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isCurrentMonth: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))
    }
}