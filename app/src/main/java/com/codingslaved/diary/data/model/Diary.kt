package com.codingslaved.diary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Diary(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT) val text: String,
    @ColumnInfo(name = "date", typeAffinity = ColumnInfo.TEXT) val date: LocalDate,
)
