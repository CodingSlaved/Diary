import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingslaved.diary.R
import com.codingslaved.diary.ui.screens.calendar.ModeHeight
import com.codingslaved.diary.ui.screens.calendar.week.WeekViewModel
import com.codingslaved.diary.ui.screens.calendar.week.WeekViewModel.Date
import java.time.LocalDate

@Composable
fun WeekCalendarView(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val dataSource = remember { WeekDataSource() }
    var weekViewModel by remember(selectedDate) {
        mutableStateOf(dataSource.getViewModel(selectedDate, selectedDate))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(ModeHeight.Week)
            .padding(horizontal = 12.dp),
    ) {
        WeekHeader(
            selectedDate = selectedDate,
            onPrevClick = {
                val newStart = weekViewModel.visibleDates.first().date.minusWeeks(1)
                weekViewModel = dataSource.getViewModel(newStart, selectedDate)
            },
            onNextClick = {
                val newStart = weekViewModel.visibleDates.last().date.plusDays(1)
                weekViewModel = dataSource.getViewModel(newStart, selectedDate)
            }
        )
        Spacer(modifier = Modifier.size(8.dp))
        WeekContent(
            modifier = Modifier.weight(1f),
            visibleDates = weekViewModel.visibleDates,
            onDateClick = { date ->
                onDateSelected(date.date)
                // Update weekViewModel to reflect the new selection
                weekViewModel = weekViewModel.copy(
                    visibleDates = weekViewModel.visibleDates.map {
                        it.copy(isSelected = it.date == date.date)
                    }
                )
            }
        )
    }
}

@Composable
private fun WeekHeader(
    selectedDate: LocalDate,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Row {
        Text(
            text = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Button(
            onClick = onPrevClick,
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
                contentDescription = R.string.calendar_next_week_btn_content_description.toString(),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = onNextClick,
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
                contentDescription = R.string.calendar_next_week_btn_content_description.toString(),
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun WeekContent(
    modifier: Modifier = Modifier,
    visibleDates: List<Date>,
    onDateClick: (WeekViewModel.Date) -> Unit,
) {
    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items (items = visibleDates) { date ->
            WeekContentItem(
                date = date,
                onDateClick
            )
        }
    }
}

@Composable
private fun WeekContentItem(
    date: WeekViewModel.Date,
    onClick: (WeekViewModel.Date) -> Unit,
) {
    Button(
        onClick = {
            onClick(date)
        },
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .defaultMinSize()
            .border(
                width = if (date.isSelected) 2.dp else 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = if (date.isSelected)
                    Color(0xFF735BF2)
                    else if (date.isToday) Color(0xFFEBE2F8)
                    else Color(0xFFCDC7EE),
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  if (date.isSelected)
                Color(0xFFEBE2F8)
            else if (date.isToday)
                Color(0xFFEBE2F8)
            else
                Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = if (date.isSelected) Color(0xFF9281EA)
                else if (date.isToday) Color(0xFF9F93E0)
                else Color(0xFFABABAB),
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = if (date.isSelected) Color(0xFF735BF2)
                else if (date.isToday) Color(0xFF8F7DF1)
                else Color(0xFF797979),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}