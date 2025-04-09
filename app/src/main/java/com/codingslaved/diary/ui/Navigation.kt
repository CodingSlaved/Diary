package com.codingslaved.diary.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codingslaved.diary.view.HomeScreen
import com.codingslaved.diary.view.calendar.CalendarScreen
import com.codingslaved.diary.view.WritingScreen
import com.codingslaved.diary.viewmodel.SharedViewModel

object Routes {
    const val HOME = "home"
    const val CALENDAR = "calendar"
    const val WRITING = "writing"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.CALENDAR,
    ) {
        composable(route = Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(route = Routes.CALENDAR) {
            CalendarScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Routes.WRITING) {
            WritingScreen(selectViewModel = viewModel)
        }
    }
}