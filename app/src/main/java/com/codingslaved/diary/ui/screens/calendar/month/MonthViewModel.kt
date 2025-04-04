package com.codingslaved.diary.ui.screens.calendar.month

import java.time.LocalDate
import java.time.YearMonth
//import java.time.format.DateTimeFormatter
//import java.time.format.TextStyle
//import java.util.Locale

data class MonthViewModel(
    val currentMonth: YearMonth,
    val selectedDate: Date,
    val visibleDates: List<Date>
) {
    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isCurrentMonth: Boolean,
        val isToday: Boolean
    ) {
//        val day: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }
}