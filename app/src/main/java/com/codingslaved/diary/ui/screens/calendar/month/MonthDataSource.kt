import android.util.Log
import com.codingslaved.diary.ui.screens.calendar.month.MonthViewModel
import java.time.LocalDate
import java.time.YearMonth

class MonthDataSource {

    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getData(currentMonth: YearMonth = YearMonth.from(today), lastSelectedDate: LocalDate): MonthViewModel {
        val visibleDates = daysInMonthArray(currentMonth)
        return toViewModel(YearMonth.of(currentMonth.year, currentMonth.month), visibleDates, lastSelectedDate)
    }

    private fun daysInMonthArray(yearMonth: YearMonth): ArrayList<LocalDate> {
        val daysArray = ArrayList<LocalDate>()
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = yearMonth.atDay(1)
        val firstDayOfWeek = firstOfMonth.dayOfWeek.value % 7

        val prevMonth = yearMonth.minusMonths(1)
        val daysInPrevMonth = prevMonth.lengthOfMonth()

        for (i in 1..42) {
            if (i <= firstDayOfWeek) {
                val day = daysInPrevMonth - (firstDayOfWeek - i)
                daysArray.add(LocalDate.of(prevMonth.year, prevMonth.month, day))
            } else if (i > daysInMonth + firstDayOfWeek) {
                if (i == 36) {
                    break
                }
                val day = i - (daysInMonth + firstDayOfWeek)
                daysArray.add(firstOfMonth.plusMonths(1).withDayOfMonth(day))
            } else {
                daysArray.add(LocalDate.of(yearMonth.year, yearMonth.month, i - firstDayOfWeek))
            }
        }
        return daysArray
    }

    private fun toViewModel(
        currentMonth: YearMonth,
        dateList: ArrayList<LocalDate>,
        lastSelectedDate: LocalDate
    ): MonthViewModel {
        return MonthViewModel(
            currentMonth = currentMonth,
            selectedDate = toItemViewModel(lastSelectedDate, lastSelectedDate.month == currentMonth.month, true),
            visibleDates = dateList.map {
                toItemViewModel(it, it.month == currentMonth.month, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemViewModel(date: LocalDate, isCurrentMonth: Boolean,  isSelectedDate: Boolean) = MonthViewModel.Date(
        isSelected = isSelectedDate,
        isCurrentMonth = isCurrentMonth,
        isToday = date.isEqual(today),
        date = date,
    )
}