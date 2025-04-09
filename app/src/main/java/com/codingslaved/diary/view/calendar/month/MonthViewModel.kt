package com.codingslaved.diary.view.calendar.month

import java.time.LocalDate
import java.time.YearMonth
//import java.time.format.DateTimeFormatter
//import java.time.format.TextStyle
//import java.util.Locale

data class MonthViewModel(
    val currentMonth: YearMonth,
    val visibleDates: List<Date>
) {
    data class Date(
        val date: LocalDate,
        val isCurrentMonth: Boolean,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
//        val day: String = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }
}