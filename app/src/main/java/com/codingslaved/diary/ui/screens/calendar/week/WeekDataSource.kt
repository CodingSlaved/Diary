import android.util.Log
import com.codingslaved.diary.ui.screens.calendar.week.WeekViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class WeekDataSource {
    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getViewModel(startDate: LocalDate, selectedDate: LocalDate): WeekViewModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val dates = (0..6).map { firstDayOfWeek.plusDays(it.toLong()) }
        return WeekViewModel(
            visibleDates = dates.map {
                WeekViewModel.Date(
                    date = it,
                    isSelected = it == selectedDate,
                    isToday = it == today
                )
            }
        )
    }
}