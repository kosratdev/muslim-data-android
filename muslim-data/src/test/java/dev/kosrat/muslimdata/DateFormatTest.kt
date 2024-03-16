package dev.kosrat.muslimdata

import dev.kosrat.muslimdata.extensions.add
import dev.kosrat.muslimdata.extensions.addDays
import dev.kosrat.muslimdata.extensions.addHours
import dev.kosrat.muslimdata.extensions.addMinutes
import dev.kosrat.muslimdata.extensions.addMonths
import dev.kosrat.muslimdata.extensions.addSeconds
import dev.kosrat.muslimdata.extensions.addYears
import dev.kosrat.muslimdata.extensions.format
import dev.kosrat.muslimdata.extensions.formatToDBDate
import dev.kosrat.muslimdata.extensions.toDate
import dev.kosrat.muslimdata.models.TimeFormat
import dev.kosrat.muslimdata.util.TestUtils.Companion.getDate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateFormatTest {
    @Test
    fun formatToDBDate_isCorrect() {
        assertEquals(getDate().formatToDBDate(), "03-11")
    }

    @Test
    fun formatTime24_enLocale_isCorrect() {
        assertEquals(getDate().format(TimeFormat.TIME_24, Locale.ENGLISH), "10:10")
    }

    @Test
    fun formatTime24_arLocale_isCorrect() {
        assertEquals(getDate().format(TimeFormat.TIME_24, Locale("ar")), "١٠:١٠")
    }

    @Test
    fun formatTime24_ckbLocale_isCorrect() {
        assertEquals(getDate().format(TimeFormat.TIME_24, Locale("ckb")), "١٠:١٠")
    }

    @Test
    fun formatTime12_enLocale_isCorrect() {
        assertEquals(getDate().format(TimeFormat.TIME_12, Locale.ENGLISH), "10:10 AM")
        assertEquals(getDate().addHours(12).format(TimeFormat.TIME_12, Locale.ENGLISH), "10:10 PM")
    }

    @Test
    fun formatTime12_arLocale_isCorrect() {
        assertEquals(getDate().format(TimeFormat.TIME_12, Locale("ar")), "١٠:١٠ ص")
        assertEquals(getDate().addHours(12).format(TimeFormat.TIME_12, Locale("ar")), "١٠:١٠ م")
    }

    @Test
    fun formatTime12_ckbLocale_isCorrect() {
        assertEquals(getDate().format(TimeFormat.TIME_12, Locale("ckb")), "١٠:١٠ ب.ن")
        assertEquals(getDate().addHours(12).format(TimeFormat.TIME_12, Locale("ckb")), "١٠:١٠ د.ن")
    }

    @Test
    fun stringToDate_dbTime_isCorrect() {
        val actualDate = getDate(2024, 2, 11, 15, 15, 0)
        assertEquals("15:15".toDate(getDate()), actualDate)
        assertEquals("10:10".toDate(getDate(hour = 11, minute = 15)), getDate())
    }

    @Test
    fun addToDate_anyPart_isCorrect() {
        assertEquals(getDate().add(Calendar.YEAR, 1), getDate(year = 2025))
        assertEquals(getDate().add(Calendar.MONTH, 2), getDate(month = 4))
        assertEquals(getDate().add(Calendar.DAY_OF_MONTH, 1), getDate(day = 12))
        assertEquals(getDate().add(Calendar.HOUR_OF_DAY, 3), getDate(hour = 13))
        assertEquals(getDate().add(Calendar.MINUTE, 2), getDate(minute = 12))
        assertEquals(getDate().add(Calendar.SECOND, 1), getDate(second = 1))
    }

    @Test
    fun addToDate_addYears_isCorrect() {
        assertEquals(getDate().addYears(2), getDate(year = 2026))
    }

    @Test
    fun addToDate_addMonths_isCorrect() {
        assertEquals(getDate().addMonths(1), getDate(month = 3))
    }

    @Test
    fun addToDate_addDays_isCorrect() {
        assertEquals(getDate().addDays(5), getDate(day = 16))
    }

    @Test
    fun addToDate_addHours_isCorrect() {
        assertEquals(getDate().addHours(5), getDate(hour = 15))
    }

    @Test
    fun addToDate_addMinutes_isCorrect() {
        assertEquals(getDate().addMinutes(5), getDate(minute = 15))
    }

    @Test
    fun addToDate_addSeconds_isCorrect() {
        assertEquals(getDate().addSeconds(10), getDate(second = 10))
    }
}