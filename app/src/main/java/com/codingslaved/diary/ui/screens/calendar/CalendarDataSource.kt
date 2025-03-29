import com.codingslaved.diary.ui.screens.calendar.CalendarViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarDataSource {

    val today: LocalDate
        get() {
            return LocalDate.now()
        }


    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarViewModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toViewModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toViewModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarViewModel {
        return CalendarViewModel(
            selectedDate = toItemViewModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemViewModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemViewModel(date: LocalDate, isSelectedDate: Boolean) = CalendarViewModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}