package dev.kosrat.muslimdata.util

import dev.kosrat.muslimdata.models.PrayerTime
import java.util.Calendar
import java.util.Date

internal class TestUtils {

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

        /**
         * Generate a prayer time with following times:
         * Fajr     = 05:00
         * Sunrise  = 07:00
         * Dhuhr    = 12:00
         * Asr      = 15:00
         * Maghrib  = 18:00
         * Isha     = 19:00
         */
        internal fun getPrayers(): PrayerTime {
            return PrayerTime(
                getDate(hour = 5, minute = 0, second = 0),
                getDate(hour = 7, minute = 0, second = 0),
                getDate(hour = 12, minute = 0, second = 0),
                getDate(hour = 15, minute = 0, second = 0),
                getDate(hour = 18, minute = 0, second = 0),
                getDate(hour = 19, minute = 0, second = 0),
            )
        }
    }
}