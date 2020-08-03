package com.kosratahmed.muslimdata.extensions

import com.kosratahmed.muslimdata.models.prayertime.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pattern: MM-dd
 */
fun Date.formatToDBDate(): String {
    val sdf = SimpleDateFormat("MM-dd", Locale.ENGLISH)
    return sdf.format(this)
}

fun Date.format(format: TimeFormat): String {
    return when (format) {
        TimeFormat.TIME_24 -> {
            val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            sdf.format(this)
        }
        TimeFormat.TIME_12 -> {
            val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            sdf.format(this)
        }
    }
}

fun String.toDate(): Date {
    val calendar = Calendar.getInstance()
    val hourMinute = this.split(":")
    val hour = hourMinute[0].toInt()
    val minute = hourMinute[1].toInt()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)

    return calendar.time
}

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}