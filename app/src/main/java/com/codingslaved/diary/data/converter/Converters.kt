package com.codingslaved.diary.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun localDateToString(date: LocalDate): String {
        return date.toString()
    }
}