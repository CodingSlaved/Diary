import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingslaved.diary.R
import com.codingslaved.diary.ui.screens.calendar.ModeHeight
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MonthCalendarView(currentDate: LocalDate) {
    Column (
        modifier = Modifier
            .height(ModeHeight.Month),
        verticalArrangement = Arrangement.Top
    ) {
        CalendarHeader(date = currentDate)
        Spacer (modifier = Modifier.size(8.dp))
        DayOfWeek()
        DaysOfMonth()
    }
}

@Composable
private fun CalendarHeader(date: LocalDate) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {},
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
            text = "${date.year}년 ${date.monthValue}월",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Button(
            onClick = {},
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
private fun DayOfWeek() {
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
private fun DaysOfMonth() {
    val days: ArrayList<String> = daysInMonthArray(LocalDate.now())
    LazyVerticalGrid(
        columns = GridCells.Fixed(7)
    ) {
        items(days.size) { index ->
            val day = days[index]
            if (day === "")
                Box {}
            else
                CalendarDay(
                    modifier = Modifier.padding(top = 10.dp),
                    day = day,
                    isToday = day == LocalDate.now().dayOfMonth.toString(),
                )
        }
    }
}

@Composable
private fun CalendarDay(
    modifier: Modifier = Modifier,
    day: String,
    isToday: Boolean
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .size(30.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .then(if (isToday) Modifier.background(Color(0xFF735BF2)) else Modifier.background(Color.Transparent))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = day,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            color = if (isToday) Color(0xFFFFFFFF) else Color(0xFF222B45),
        )
    }
}

private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
    val daysInMonthArray = ArrayList<String>()
    val yearMonth = YearMonth.from(date)

    val daysInMonth = yearMonth.lengthOfMonth()

    val firstOfMonth: LocalDate = LocalDate.now().withDayOfMonth(1)
    val firstDayOfWeek = firstOfMonth.dayOfWeek.value

    for (i in 1..42) {
        if (i <= firstDayOfWeek || i > daysInMonth + firstDayOfWeek) {
            daysInMonthArray.add("")
        } else {
            daysInMonthArray.add((i - firstDayOfWeek).toString())
        }
    }
    return daysInMonthArray
}
