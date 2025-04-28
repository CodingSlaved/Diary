import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codingslaved.diary.R
import com.codingslaved.diary.view.calendar.ModeHeight
import com.codingslaved.diary.view.calendar.month.MonthViewModel
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MonthCalendarView(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    viewModel: MonthViewModel = viewModel<MonthViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(selectedDate) {
        viewModel.updateSelectedDate(selectedDate)
    }

    Column (
        modifier = Modifier
            .height(ModeHeight.Month),
        verticalArrangement = Arrangement.Top
    ) {
        MonthHeader(
            yearMonth = uiState.currentMonth,
            onPrevClick = {
                val newStart = uiState.currentMonth.minusMonths(1)
                viewModel.updateCurrentMonth(newStart)
            },
            onNextClick = {
                val newStart = uiState.currentMonth.plusMonths(1)
                viewModel.updateCurrentMonth(newStart)
            }
        )
        Spacer (modifier = Modifier.size(8.dp))
        MonthDayOfWeek()
        MonthDaysOfMonth(
            visibleDates = uiState.visibleDates,
            onDayClick = {
                onDateSelected(it.date)
            }
        )
    }
}

@Composable
private fun MonthHeader(
    yearMonth : YearMonth,
    onPrevClick: (YearMonth) -> Unit = {},
    onNextClick: (YearMonth) -> Unit = {}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                val prevYearMonth = yearMonth.minusMonths(1)
                onPrevClick(prevYearMonth)
            },
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 0.dp,
                    minHeight = 0.dp
                )
                .size(36.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFFCED3DE))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = R.string.calendar_next_month_btn_content_description.toString(),
                tint = Color.Black
            )
        }
        Text(
            text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Button(
            onClick = {
                val nextYearMonth = yearMonth.plusMonths(1)
                onNextClick(nextYearMonth)
            },
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 0.dp,
                    minHeight = 0.dp
                )
                .size(36.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFFCED3DE))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = R.string.calendar_next_month_btn_content_description.toString(),
                tint = Color.Black
            )
        }
    }
}

private val array: Array<String> = arrayOf("일", "월", "화", "수", "목", "금", "토")

@Composable
private fun MonthDayOfWeek() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        array.map { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color(0xFF8F9BB3)
            )
        }
    }
}

@Composable
private fun MonthDaysOfMonth(
    visibleDates: List<MonthViewModel.Date>,
    onDayClick: (MonthViewModel.Date) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7)
    ) {
        items (visibleDates) { date ->
                CalendarDay(
                    modifier = Modifier.padding(top = 10.dp),
                    date = date,
                    onDateClick = onDayClick
                )
        }
    }
}

@Composable
private fun CalendarDay(
    modifier: Modifier = Modifier,
    date: MonthViewModel.Date,
    onDateClick: (MonthViewModel.Date) -> Unit = {},
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .size(30.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .then(
                if (date.isSelected)
                    Modifier.background(Color(0xFF735BF2))
                else if (date.isToday)
                    Modifier.border(
                        width = 1.dp,
                        color = Color(0xFF735BF2),
                        shape = RoundedCornerShape(8.dp)
                    )
                else
                    Modifier.background(Color.Transparent)
            )
            .clickable(
                enabled = true,
                onClick = {
                    onDateClick(date)
                }
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = date.date.dayOfMonth.toString(),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            color =
                    if (date.isSelected)
                        Color(0xFFFFFFFF)
                    else if (!date.isCurrentMonth)
                        Color(0xFF8F9BB3)
                    else
                        Color(0xFF222B45),
        )
    }
}