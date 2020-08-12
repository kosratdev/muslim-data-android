package com.kosratahmed.muslimdata.models.prayertime

import com.kosratahmed.muslimdata.extensions.addDays
import com.kosratahmed.muslimdata.extensions.addHours
import com.kosratahmed.muslimdata.extensions.addMinutes
import com.kosratahmed.muslimdata.extensions.format
import java.util.*

/**
 * Prayer time class that holds all prayer times for a day.
 */
class PrayerTime internal constructor(
    fajr: Date,
    sunrise: Date,
    dhuhr: Date,
    asr: Date,
    maghrib: Date,
    isha: Date
) {
    var fajr: Date
        private set
    var sunrise: Date
        private set
    var dhuhr: Date
        private set
    var asr: Date
        private set
    var maghrib: Date
        private set
    var isha: Date
        private set

    init {
        this.fajr = fajr
        this.sunrise = sunrise
        this.dhuhr = dhuhr
        this.asr = asr
        this.maghrib = maghrib
        this.isha = isha
    }

    /**
     * Apply the given offset on the prayer times.
     */
    internal fun applyOffset(offsets: IntArray) {
        fajr = fajr.addMinutes(offsets[0])
        sunrise = sunrise.addMinutes(offsets[1])
        dhuhr = dhuhr.addMinutes(offsets[2])
        asr = asr.addMinutes(offsets[3])
        maghrib = maghrib.addMinutes(offsets[4])
        isha = isha.addMinutes(offsets[5])
    }

    /**
     * Adjust prayer times for day lighting save.
     */
    internal fun adjustDST() {
        val timeZone = TimeZone.getDefault()

        if (timeZone.inDaylightTime(Date())) {
            fajr = fajr.addHours(1)
            sunrise = sunrise.addHours(1)
            dhuhr = dhuhr.addHours(1)
            asr = asr.addHours(1)
            maghrib = maghrib.addHours(1)
            isha = isha.addHours(1)
        }
    }

    /**
     * Format prayer times by given format and the default one is 24 hours format and also it will
     * return a formatted prayer time list.
     */
    fun formatPrayerTime(format: TimeFormat = TimeFormat.TIME_24): Array<String> {
        return arrayOf(
            fajr.format(format),
            sunrise.format(format),
            dhuhr.format(format),
            asr.format(format),
            maghrib.format(format),
            isha.format(format)
        )
    }

    /**
     * Find and return next prayer time index.
     */
    fun nextPrayerTimeIndex(): Int {
        val prayers = arrayOf(fajr, sunrise, dhuhr, asr, maghrib, isha)
        val now = Date()
        prayers.forEachIndexed { index, date ->
            if (date.after(now)) {
                return index
            }
        }
        return -1
    }

    /**
     * Find and return the next prayer time interval.
     */
    fun nextPrayerTimeInterval(): Long {
        val prayers = arrayOf(fajr, sunrise, dhuhr, asr, maghrib, isha)
        val index = nextPrayerTimeIndex()

        if (index == -1) {
            return fajr.addDays(1).time - Date().time
        }

        return prayers[index].time - Date().time
    }

    /**
     * Find and return next prayer time remaining.
     */
    fun nextPrayerTimeRemaining(): String {
        var time = nextPrayerTimeInterval()
        val hour = time / 3600000
        time -= hour * 3600000
        val minute = time / 60000
        time -= minute * 60000
        val second = time / 1000

        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
}