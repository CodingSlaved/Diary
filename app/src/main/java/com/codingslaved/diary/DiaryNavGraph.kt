package com.codingslaved.diary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codingslaved.diary.DiaryDestinationsArgs.DATE_ARG
import com.codingslaved.diary.view.WritingScreen
import com.codingslaved.diary.view.calendar.CalendarScreen
import java.time.LocalDate

@Composable
fun DiaryNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = DiaryDestinations.CALENDAR_ROUTE,
    navActions: DiaryNavigationActions = remember(navController) {
        DiaryNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            DiaryDestinations.CALENDAR_ROUTE,
        ) { entry ->
            CalendarScreen(
                onAddEditDiary = { date -> navActions.navigateToAddEditDiary(date) },
            )
        }
        composable(
            DiaryDestinations.ADD_EDIT_DIARY_ROUTE,
            arguments = listOf(
                navArgument(DATE_ARG) { type = NavType.StringType },
            )
        ) { entry ->
            val dateString = entry.arguments?.getString(DATE_ARG)!!
            WritingScreen(
                date = LocalDate.parse(dateString),
                onBack = { navController.popBackStack() }
            )
        }
    }
}