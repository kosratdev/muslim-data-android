package dev.kosrat.muslimdata.util

import java.util.Calendar
import java.util.Date

internal class TestDateUtil {

    companion object {
        /**
         * Generate date (2024/3/11 10:10:00). Also, gets value for each portion of the date.
         */
        internal fun getDate(
            year: Int = 2024,
            month: Int = 2,
            day: Int = 11,
            hour: Int = 10,
            minute: Int = 10,
            second: Int = 0
        ): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, hour, minute, second)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.time
        }
    }
}