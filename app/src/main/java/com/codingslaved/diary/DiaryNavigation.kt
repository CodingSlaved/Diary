package com.codingslaved.diary

import android.util.Log
import androidx.navigation.NavHostController
import com.codingslaved.diary.DiaryDestinationsArgs.DATE_ARG
import com.codingslaved.diary.DiaryScreens.CALENDAR_SCREEN
import com.codingslaved.diary.DiaryScreens.ADD_EDIT_DIARY_SCREEN

private object DiaryScreens {
    const val CALENDAR_SCREEN = "calendar"
    const val ADD_EDIT_DIARY_SCREEN = "addEditDiary"
}

object DiaryDestinationsArgs {
    const val DATE_ARG = "date"
}

object DiaryDestinations {
    const val CALENDAR_ROUTE = CALENDAR_SCREEN
    const val ADD_EDIT_DIARY_ROUTE = "$ADD_EDIT_DIARY_SCREEN/{$DATE_ARG}"
}

class DiaryNavigationActions(private val navController: NavHostController) {

    fun navigateToCalendar() {
        navController.navigate(CALENDAR_SCREEN)
    }

    fun navigateToAddEditDiary(date: String) {
        Log.i("DiaryNavigationActions", "navigateToAddEditDiary: $date")
        navController.navigate(
            "$ADD_EDIT_DIARY_SCREEN/$date"
        )
    }
}