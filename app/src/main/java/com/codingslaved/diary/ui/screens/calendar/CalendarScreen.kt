package com.codingslaved.diary.ui.screens.calendar


import MonthCalendarView
import WeekCalendarView
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.codingslaved.diary.R
import com.codingslaved.diary.ui.Routes

enum class Mode {
    MONTH,
    WEEK
}

object ModeHeight {
    val Month = 340.dp
    val Week = 150.dp
}

fun calculateHeight(mode: Mode): Dp {
    return when (mode) {
        Mode.MONTH -> ModeHeight.Month
        Mode.WEEK -> ModeHeight.Week
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavController) {
    var currentMode by remember { mutableStateOf(Mode.MONTH) }
    var rawHeight by remember { mutableStateOf(calculateHeight(currentMode)) } // Dp 단위로 관리
    var isDragging by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    val targetHeight by remember(rawHeight, isDragging) {
        mutableStateOf(
            if (isDragging) rawHeight
            else if (rawHeight <= (ModeHeight.Month + ModeHeight.Week) / 2) ModeHeight.Week
            else ModeHeight.Month
        )
    }

    LaunchedEffect(targetHeight) {
        currentMode = if (targetHeight == ModeHeight.Week) Mode.WEEK else Mode.MONTH
    }

    Scaffold { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                )
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Header(
                    mode = currentMode,
                    onChangeMode = { mode ->
                        rawHeight = calculateHeight(mode)
                    }
                )
                Box(
                    modifier = Modifier
                        .animateContentSize()
                        .height(targetHeight)
                ) {
                    Crossfade(
                        targetState = currentMode,
                        label = "mode",
                    ) { mode ->
                        when (mode) {
                            Mode.MONTH -> MonthCalendarView()
                            Mode.WEEK -> WeekCalendarView()
                        }
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
            }
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
            ) {
                DragHandler(
                    onDragStart = { isDragging = true },
                    onDrag = { offset ->
                        with(density) {
                            val dragAmountDp = offset.toDp()
                            val newHeight = rawHeight + dragAmountDp
                            rawHeight = max(ModeHeight.Week, min(ModeHeight.Month, newHeight))
                        }
                    },
                    onDragEnd = { isDragging = false }
                )
                ScheduleContent(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = innerPadding.calculateBottomPadding()
                        )
                )
            }
        }
    }
}

@Composable
private fun Header(mode: Mode = Mode.MONTH, onChangeMode: (Mode) -> Unit = {}) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Calendar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = {
                onChangeMode(if (mode == Mode.WEEK) Mode.MONTH else Mode.WEEK)
            }) {
                Icon(
                    imageVector =
                        if (mode == Mode.MONTH)
                            Icons.Default.CalendarViewWeek
                        else
                            Icons.Default.CalendarViewMonth,
                    contentDescription = R.string.change_calendar_view_btn_content_description.toString())
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = R.string.setting_btn_content_description.toString())
            }
        }
    }
}

@Composable
private fun ScheduleContent(navController: NavController, modifier: Modifier = Modifier) {

    Card (
        onClick = {navController.navigate(Routes.WRITING)},
        modifier = modifier
            .padding(
                top = 10.dp,
                bottom = 10.dp,
                start = 12.dp,
                end = 12.dp
            ),

    ) {}

}

@Composable
private fun DragHandler(
    onDragStart: () -> Unit,
    onDrag: (Float) -> Unit,
    onDragEnd: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { onDragStart() },
                    onVerticalDrag = { _, dragAmount -> onDrag(dragAmount) },
                    onDragEnd = { onDragEnd() }
                )
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(8.dp)
                .background(color = Color(0xFFCED3DE), shape = CircleShape)
        )
    }
}

//@Preview
//@Composable
//fun CalendarScreenPreview() {
//    CalendarScreen()
//}