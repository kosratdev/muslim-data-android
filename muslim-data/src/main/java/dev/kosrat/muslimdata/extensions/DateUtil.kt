package dev.kosrat.muslimdata.extensions

import dev.kosrat.muslimdata.models.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Format date to match the database date style.
 * Pattern: MM-dd
 */
internal fun Date.formatToDBDate(): String {
    val sdf = SimpleDateFormat("MM-dd", Locale.ENGLISH)
    return sdf.format(this)
}

/**
 * Format time to be 12 or 24 time format.
 */
internal fun Date.format(format: TimeFormat, locale: Locale): String {
    return when (format) {
        TimeFormat.TIME_24 -> {
            val sdf = SimpleDateFormat("HH:mm", locale)
            sdf.format(this)
        }
        TimeFormat.TIME_12 -> {
            val sdf = SimpleDateFormat("hh:mm a", locale)
            sdf.format(this)
        }
    }
}

/**
 * Convert database string date to date.
 */
internal fun String.toDate(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val hourMinute = this.split(":")
    val hour = hourMinute[0].toInt()
    val minute = hourMinute[1].toInt()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.time
}

/**
 * Add field date to the current date.
 */
internal fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

/**
 * Add years to the current date.
 */
internal fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

/**
 * Add months to the current date.
 */
internal fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

/**
 * Add days to the current date.
 */
internal fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

/**
 * Add hours to the current date.
 */
internal fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

/**
 * Add minutes to the current date.
 */
internal fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

/**
 * Add seconds to the current date.
 */
internal fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}