package com.codingslaved.diary.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.codingslaved.diary.data.model.Diary
import com.codingslaved.diary.data.repository.DiaryRepository

class DiaryEntryViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {
    var diaryUiState by mutableStateOf(DiaryUiState())
        private set

    fun updateUiState(diaryDetails: DiaryDetails) {
        diaryUiState =
            DiaryUiState(diaryDetails = diaryDetails, isEntryValid = validateInput(diaryDetails))
    }

    private fun validateInput(uiState: DiaryDetails = diaryUiState.diaryDetails): Boolean {
        return with(uiState) {
            id.isNotBlank() && text.isNotBlank() && date.isNotBlank()
        }
    }

    fun getDiaryByDate(date: String) = diaryRepository.getDiary(date)

    suspend fun saveDiary() {
        if (validateInput()) {
            diaryRepository.insert(diaryUiState.diaryDetails.toItem())
        }
    }

    suspend fun updateDiary() {
        if (validateInput()) {
            diaryRepository.update(diaryUiState.diaryDetails.toItem())
        }
    }
}

data class DiaryUiState(
    val diaryDetails: DiaryDetails = DiaryDetails(),
    val isEntryValid: Boolean = false
)

data class DiaryDetails(
    val id: String = "",
    val text: String = "",
    val date: String = "",
)

fun DiaryDetails.toItem(): Diary = Diary(
    id = id,
    text = text,
    date = date,
)