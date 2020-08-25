package dev.kosrat.muslimdata.models

import dev.kosrat.muslimdata.extensions.addDays
import dev.kosrat.muslimdata.extensions.addHours
import dev.kosrat.muslimdata.extensions.addMinutes
import dev.kosrat.muslimdata.extensions.format
import java.util.*

/**
 * Prayer time class that holds all prayer times for a day.
 */
data class PrayerTime internal constructor(
    private var _fajr: Date,
    private var _sunrise: Date,
    private var _dhuhr: Date,
    private var _asr: Date,
    private var _maghrib: Date,
    private var _isha: Date
) {

    val fajr: Date get() = _fajr
    val sunrise: Date get() = _sunrise
    val dhuhr: Date get() = _dhuhr
    val asr: Date get() = _asr
    val maghrib: Date get() = _maghrib
    val isha: Date get() = _isha

    /**
     * Apply the given offset on the prayer times.
     */
    internal fun applyOffset(offsets: IntArray) {
        _fajr = _fajr.addMinutes(offsets[0])
        _sunrise = _sunrise.addMinutes(offsets[1])
        _dhuhr = _dhuhr.addMinutes(offsets[2])
        _asr = _asr.addMinutes(offsets[3])
        _maghrib = _maghrib.addMinutes(offsets[4])
        _isha = _isha.addMinutes(offsets[5])
    }

    /**
     * Adjust prayer times for day lighting save.
     */
    internal fun adjustDST() {
        val timeZone = TimeZone.getDefault()

        if (timeZone.inDaylightTime(Date())) {
            _fajr = _fajr.addHours(1)
            _sunrise = _sunrise.addHours(1)
            _dhuhr = _dhuhr.addHours(1)
            _asr = _asr.addHours(1)
            _maghrib = _maghrib.addHours(1)
            _isha = _isha.addHours(1)
        }
    }

    /**
     * Format prayer times by given format and the default one is 24 hours format and also it will
     * return a formatted prayer time list.
     */
    fun formatPrayerTime(format: TimeFormat = TimeFormat.TIME_24): Array<String> {
        return arrayOf(
            _fajr.format(format),
            _sunrise.format(format),
            _dhuhr.format(format),
            _asr.format(format),
            _maghrib.format(format),
            _isha.format(format)
        )
    }

    /**
     * Find and return next prayer time index.
     */
    fun nextPrayerTimeIndex(): Int {
        val prayers = arrayOf(_fajr, _sunrise, _dhuhr, _asr, _maghrib, _isha)
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
        val prayers = arrayOf(_fajr, _sunrise, _dhuhr, _asr, _maghrib, _isha)
        val index = nextPrayerTimeIndex()

        if (index == -1) {
            return _fajr.addDays(1).time - Date().time
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

    override fun toString(): String {
        return "PrayerTime(fajr=$fajr, sunrise=$sunrise, dhuhr=$dhuhr, asr=$asr, maghrib=$maghrib, ish=$isha)"
    }
}