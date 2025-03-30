import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingslaved.diary.R
import com.codingslaved.diary.ui.screens.calendar.ModeHeight
import com.codingslaved.diary.ui.screens.calendar.week.WeekViewModel
import java.time.LocalDate

@Composable
fun WeekCalendarView() {
    val dataSource = WeekDataSource()
    var weekViewModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(ModeHeight.Week)
            .padding(horizontal = 12.dp),
    ) {
        WeekHeader(
            data = weekViewModel,
            onPrevClick = { startDate ->
                val finalStartDate = startDate.minusDays(1)
                weekViewModel = dataSource.getData(startDate = finalStartDate, lastSelectedDate = weekViewModel.selectedDate.date)
            },
            onNextClick = { endDate ->
                val finalStartDate = endDate.plusDays(2)
                weekViewModel = dataSource.getData(startDate = finalStartDate, lastSelectedDate = weekViewModel.selectedDate.date)
            }
        )
        Spacer(modifier = Modifier.size(8.dp))
        WeekContent(
            modifier = Modifier.weight(1f),
            data = weekViewModel,
            onDateClick = { date ->
                weekViewModel = weekViewModel.copy(
                selectedDate = date,
                visibleDates = weekViewModel.visibleDates.map {
                    it.copy(
                        isSelected = it.date.isEqual(date.date)
                    )
                }
            )
        })
    }
}

@Composable
private fun WeekHeader(
    data: WeekViewModel,
    onPrevClick: (LocalDate) -> Unit,
    onNextClick: (LocalDate) -> Unit,
) {
    Row {
        Text(
            text = if (data.selectedDate.isToday) {
                "오늘"
            } else {
                "${data.selectedDate.date.year}년 ${data.selectedDate.date.monthValue}월 ${data.selectedDate.date.dayOfMonth}일"
            },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Button(
            onClick = {
                onPrevClick(data.startDate.date)
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
                contentDescription = R.string.calendar_next_week_btn_content_description.toString(),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {
                onNextClick(data.endDate.date)
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
                contentDescription = R.string.calendar_next_week_btn_content_description.toString(),
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun WeekContent(
    modifier: Modifier = Modifier,
    data: WeekViewModel,
    onDateClick: (WeekViewModel.Date) -> Unit,
) {
    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items (items = data.visibleDates) { date ->
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
                    else Color(0xFFCDC7EE),
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  if (date.isSelected)
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
                color = if (date.isSelected) Color(0xFF9281EA) else Color(0xFFABABAB),
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = if (date.isSelected) Color(0xFF735BF2) else Color(0xFF797979),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}