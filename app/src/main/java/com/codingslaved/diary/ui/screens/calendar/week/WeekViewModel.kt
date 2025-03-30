package com.codingslaved.diary.ui.screens.calendar.week

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class WeekViewModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
) {

    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }
}