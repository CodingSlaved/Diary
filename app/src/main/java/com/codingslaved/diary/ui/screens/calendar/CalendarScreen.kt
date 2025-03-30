package com.codingslaved.diary.ui.screens.calendar


import MonthCalendarView
import WeekCalendarView
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingslaved.diary.R

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
fun CalendarScreen() {
    var currentMode by remember { mutableStateOf(Mode.MONTH) }

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
                    onChnageMode = {
                        currentMode = if (currentMode == Mode.MONTH) Mode.WEEK else Mode.MONTH
                    }
                )
                Box(
                    modifier = Modifier
                        .animateContentSize()
                        .height(calculateHeight(currentMode))
                ) {
                    Crossfade (
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
            Box(modifier = Modifier.weight(1f)) {
                ScheduleContent(
                    modifier = Modifier
                        .padding(
                            bottom = innerPadding.calculateBottomPadding()
                        )
                )
            }
        }
    }
}

@Composable
private fun Header(mode: Mode = Mode.MONTH, onChnageMode: () -> Unit = {}) {
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
            IconButton(onClick = onChnageMode) {
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
private fun ScheduleContent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
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
            .fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(8.dp)
                    .background(
                        color = Color(0xFFCED3DE),
                        shape = CircleShape
                    )
            )
        }
        Column (
            modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 12.dp,
                    end = 12.dp
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card (
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color.Green)
            ) {
                Text(text = "asdfd")
            }
        }
    }
}

@Preview
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}