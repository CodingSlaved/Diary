package com.codingslaved.diary.ui.screens.calendar.week

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class WeekViewModel(
    val visibleDates: List<Date>
) {
    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }
}