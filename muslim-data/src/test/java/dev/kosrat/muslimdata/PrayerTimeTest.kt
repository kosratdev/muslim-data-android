package dev.kosrat.muslimdata

import dev.kosrat.muslimdata.models.TimeFormat
import dev.kosrat.muslimdata.util.TestUtils.Companion.getDate
import dev.kosrat.muslimdata.util.TestUtils.Companion.getPrayers
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class PrayerTimeTest {

    @Test
    fun prayerTime_applyOffset_isCorrect() {
        val prayer = getPrayers()
        prayer.applyOffset(intArrayOf(1, 2, 3, 2, 1, 5))

        assertEquals(prayer.fajr, getDate(hour = 5, minute = 1, second = 0))
        assertEquals(prayer.sunrise, getDate(hour = 7, minute = 2, second = 0))
        assertEquals(prayer.dhuhr, getDate(hour = 12, minute = 3, second = 0))
        assertEquals(prayer.asr, getDate(hour = 15, minute = 2, second = 0))
        assertEquals(prayer.maghrib, getDate(hour = 18, minute = 1, second = 0))
        assertEquals(prayer.isha, getDate(hour = 19, minute = 5, second = 0))
    }

    @Test
    fun prayerTime_formatPrayers_isCorrect() {
        val prayer = getPrayers()
        val formattedPrayers = prayer.formatPrayerTime(TimeFormat.TIME_12, Locale.ENGLISH)

        assertEquals(formattedPrayers[0], "05:00 AM")
        assertEquals(formattedPrayers[1], "07:00 AM")
        assertEquals(formattedPrayers[2], "12:00 PM")
        assertEquals(formattedPrayers[3], "03:00 PM")
        assertEquals(formattedPrayers[4], "06:00 PM")
        assertEquals(formattedPrayers[5], "07:00 PM")
    }

    @Test
    fun prayerTime_getOperator_isCorrect() {
        val prayer = getPrayers()

        assertEquals(prayer[0], getDate(hour = 5, minute = 0, second = 0))
        assertEquals(prayer[1], getDate(hour = 7, minute = 0, second = 0))
        assertEquals(prayer[2], getDate(hour = 12, minute = 0, second = 0))
        assertEquals(prayer[3], getDate(hour = 15, minute = 0, second = 0))
        assertEquals(prayer[4], getDate(hour = 18, minute = 0, second = 0))
        assertEquals(prayer[5], getDate(hour = 19, minute = 0, second = 0))
    }
}