import com.codingslaved.diary.ui.screens.calendar.month.MonthViewModel
import com.codingslaved.diary.ui.screens.calendar.week.WeekViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

// Optimized Month Data Source
class MonthDataSource {
    private val today: LocalDate = LocalDate.now()

    fun getData(currentMonth: YearMonth, selectedDate: LocalDate): MonthViewModel {
        val firstOfMonth = currentMonth.atDay(1)
        val firstDayOfWeek = firstOfMonth.dayOfWeek.value % 7
        val daysInMonth = currentMonth.lengthOfMonth()
        val prevMonth = currentMonth.minusMonths(1)
        val daysInPrevMonth = prevMonth.lengthOfMonth()

        val dates = ArrayList<LocalDate>(42)
        for (i in 0..41) {
            when {
                i < firstDayOfWeek -> dates.add(prevMonth.atDay(daysInPrevMonth - firstDayOfWeek + i + 1))
                i < daysInMonth + firstDayOfWeek -> dates.add(firstOfMonth.plusDays((i - firstDayOfWeek).toLong()))
                i < 35 -> dates.add(currentMonth.plusMonths(1).atDay(i - daysInMonth - firstDayOfWeek + 1))
                else -> break
            }
        }

        return MonthViewModel(
            currentMonth = currentMonth,
            visibleDates = dates.map {
                MonthViewModel.Date(
                    date = it,
                    isCurrentMonth = it.month == currentMonth.month,
                    isSelected = it == selectedDate,
                    isToday = it == today
                )
            }
        )
    }
}