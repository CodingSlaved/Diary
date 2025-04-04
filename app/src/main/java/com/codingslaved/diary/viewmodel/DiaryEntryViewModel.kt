package com.codingslaved.diary.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.codingslaved.diary.data.model.Diary
import com.codingslaved.diary.data.repository.DiaryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DiaryEntryViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {
    var diaryUiState by mutableStateOf(DiaryUiState())
        private set

    fun updateUiState(diaryDetails: DiaryDetails) {
        diaryUiState =
            DiaryUiState(diaryDetails = diaryDetails, isEntryValid = validateInput(diaryDetails))
    }

    private fun validateInput(uiState: DiaryDetails = diaryUiState.diaryDetails): Boolean {
        return with(uiState) {
            text.isNotBlank()
        }
    }

    suspend fun saveDiary() {
        if (validateInput()) {

            diaryRepository.insert(diaryUiState.diaryDetails.toItem())
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
)

@OptIn(ExperimentalUuidApi::class)
fun DiaryDetails.toItem(): Diary = Diary(
    id = Uuid.random().toString(),
    text = text,
)