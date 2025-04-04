package com.codingslaved.diary.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codingslaved.diary.ui.screens.home.HomeScreen
import com.codingslaved.diary.ui.screens.calendar.CalendarScreen
import com.codingslaved.diary.view.WritingScreen

object Routes {
    const val HOME = "home"
    const val CALENDAR = "calendar"
    const val WRITING = "writing"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CALENDAR,
    ) {
        composable(route = Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(route = Routes.CALENDAR) {
            CalendarScreen(navController = navController)
        }
        composable(route = Routes.WRITING) {
            WritingScreen()
        }
    }
}