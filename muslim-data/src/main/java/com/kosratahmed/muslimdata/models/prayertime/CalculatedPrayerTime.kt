package com.kosratahmed.muslimdata.models.prayertime

import com.kosratahmed.muslimdata.extensions.toDate
import com.kosratahmed.muslimdata.models.City
import java.util.*
import kotlin.math.*

@Suppress("NAME_SHADOWING")
class CalculatedPrayerTime(private val attribute: PrayerAttribute) {

    // ---------------------- Global Variables --------------------
    private var lat = 0.0 // latitude
    private var lng = 0.0 // longitude
    private var timeZone = 0.0 // time-zone
    private var jDate = 0.0 // Julian date
    private val invalidTime = "-----" // The string used for invalid times
    private var numIterations = 0 // number of iterations needed to compute times

    // ------------------- Calc Method Parameters --------------------
    private val methodParams: HashMap<CalculationMethod, DoubleArray>

    init {
        numIterations = 1 // number of iterations needed to compute times
        methodParams = CalculationMethod.methodList()
    }

    // ---------------------- Trigonometric Functions -----------------------
    // range reduce angle in degrees.
    private fun fixAngle(a: Double): Double {
        var a = a
        a -= 360 * floor(a / 360.0)
        return if (a < 0) a + 360 else a
    }

    // range reduce hours to 0..23
    private fun fixHour(a: Double): Double {
        var a = a
        a -= 24.0 * floor(a / 24.0)
        return if (a < 0) a + 24 else a
    }

    // radian to degree
    private fun radiansToDegrees(alpha: Double): Double {
        return alpha * 180.0 / Math.PI
    }

    // degree to radian
    private fun degreesToRadians(alpha: Double): Double = alpha * Math.PI / 180.0

    // degree sin
    private fun dSin(d: Double): Double = sin(degreesToRadians(d))

    // degree cos
    private fun dCos(d: Double): Double = cos(degreesToRadians(d))

    // degree tan
    private fun dTan(d: Double): Double = tan(degreesToRadians(d))

    // degree arc sin
    private fun dArcSin(x: Double): Double = radiansToDegrees(asin(x))

    // degree arc cos
    private fun dArcCos(x: Double): Double = radiansToDegrees(acos(x))

    // degree arc tan 2
    private fun dArcTan2(y: Double, x: Double): Double = radiansToDegrees(atan2(y, x))

    // degree arc cot
    private fun dArcCot(x: Double): Double = radiansToDegrees(atan2(1.0, x))

    // ---------------------- Julian Date Functions -----------------------
    // calculate julian date from a calendar date
    private fun julianDate(year: Int, month: Int, day: Int): Double {
        var year = year
        var month = month
        if (month <= 2) {
            year -= 1
            month += 12
        }
        val a = floor(year / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return (floor(365.25 * (year + 4716)) + floor(30.6001 * (month + 1)) + day + b) - 1524.5
    }

    // ---------------------- Calculation Functions -----------------------
    // References:
    // http://www.ummah.net/astronomy/saltime
    // http://aa.usno.navy.mil/faq/docs/SunApprox.html
    // compute declination angle of sun and equation of time
    private fun sunPosition(jd: Double): DoubleArray {
        val d1 = jd - 2451545
        val g = fixAngle(357.529 + 0.98560028 * d1)
        val q = fixAngle(280.459 + 0.98564736 * d1)
        val l = fixAngle(q + 1.915 * dSin(g) + 0.020 * dSin(2 * g))

        val e = 23.439 - 0.00000036 * d1
        val d2 = dArcSin(dSin(e) * dSin(l))
        var ra = dArcTan2(dCos(e) * dSin(l), dCos(l)) / 15.0
        ra = fixHour(ra)
        val eqt = q / 15.0 - ra
        val sPosition = DoubleArray(2)
        sPosition[0] = d2
        sPosition[1] = eqt
        return sPosition
    }

    // compute equation of time
    private fun equationOfTime(jd: Double): Double = sunPosition(jd)[1]

    // compute declination angle of sun
    private fun sunDeclination(jd: Double): Double = sunPosition(jd)[0]

    // compute mid-day (Dhuhr, Zawal) time
    private fun computeMidDay(t: Double): Double {
        val t = equationOfTime(jDate + t)
        return fixHour(12 - t)
    }

    // compute time for a given angle G
    private fun computeTime(G: Double, t: Double): Double {
        val d = sunDeclination(jDate + t)
        val z = computeMidDay(t)
        val beg = -dSin(G) - dSin(d) * dSin(lat)
        val mid = dCos(d) * dCos(lat)
        val v = dArcCos(beg / mid) / 15.0
        return z + if (G > 90) -v else v
    }

    // compute the time of Asr
    // Shafii: step=1, Hanafi: step=2
    private fun computeAsr(step: Double, t: Double): Double {
        val d = sunDeclination(jDate + t)
        val g = -dArcCot(step + dTan(abs(lat - d)))
        return computeTime(g, t)
    }

    // ---------------------- Misc Functions -----------------------
    // compute the difference between two times
    private fun timeDiff(time1: Double, time2: Double): Double = fixHour(time2 - time1)

    // -------------------- Interface Functions --------------------

    // return prayer times for a given date
    fun getPrayerTimes(city: City, date: Date): PrayerTime {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DATE]
        timeZone = TimeZone.getDefault().getOffset(date.time) / (1000.0 * 60.0 * 60.0)

        lat = city.latitude
        lng = city.longitude
        jDate = julianDate(year, month, day)
        val lonDiff = city.longitude / (15.0 * 24.0)
        jDate -= lonDiff

        val cTime = computeDayTimes()
        return PrayerTime(
            cTime[0].toDate(),
            cTime[1].toDate(),
            cTime[2].toDate(),
            cTime[3].toDate(),
            cTime[4].toDate(),
            cTime[5].toDate()
        )
    }

    // convert double hours to 24h format
    private fun floatToTime24(time: Double): String {
        var time = time
        val result: String
        if (java.lang.Double.isNaN(time)) {
            return invalidTime
        }
        time = fixHour(time + 0.5 / 60.0) // add 0.5 minutes to round
        val hours = floor(time).toInt()
        val minutes = floor((time - hours) * 60.0)
        result = if (hours in 0..9 && minutes >= 0 && minutes <= 9) {
            "0" + hours + ":0" + minutes.roundToInt()
        } else if (hours in 0..9) {
            "0" + hours + ":" + minutes.roundToInt()
        } else if (minutes in 0.0..9.0) {
            hours.toString() + ":0" + minutes.roundToInt()
        } else {
            hours.toString() + ":" + minutes.roundToInt()
        }
        return result
    }

    // ---------------------- Compute Prayer Times -----------------------
    // compute prayer times at given julian date
    private fun computeTimes(times: DoubleArray): DoubleArray {
        val t = dayPortion(times)
        val fajr = computeTime(
            180 - methodParams[attribute.calculationMethod]!![0], t[0]
        )
        val sunrise = computeTime(180 - 0.833, t[1])
        val dhuhr = computeMidDay(t[2])
        val asr = computeAsr(1 + attribute.asrMethod.value.toDouble(), t[3])
        val sunset = computeTime(0.833, t[4])
        val maghrib = computeTime(methodParams[attribute.calculationMethod]!![2], t[5])
        val isha = computeTime(methodParams[attribute.calculationMethod]!![4], t[6])
        return doubleArrayOf(fajr, sunrise, dhuhr, asr, sunset, maghrib, isha)
    }

    // compute prayer times at given julian date
    private fun computeDayTimes(): ArrayList<String> {
        var times = doubleArrayOf(5.0, 6.0, 12.0, 13.0, 18.0, 18.0, 18.0) // default times
        for (i in 1..numIterations) {
            times = computeTimes(times)
        }
        times = adjustTimes(times)
        return adjustTimesFormat(times)
    }

    // adjust times in a prayer time array
    private fun adjustTimes(times: DoubleArray): DoubleArray {
        var times = times
        for (i in times.indices) {
            times[i] += timeZone - lng / 15
        }
        if (methodParams[attribute.calculationMethod]!![1] == 1.0) // Maghrib
        {
            times[5] = times[4] + methodParams[attribute.calculationMethod]!![2] / 60
        }
        if (methodParams[attribute.calculationMethod]!![3] == 1.0) // Isha
        {
            times[6] = times[5] + methodParams[attribute.calculationMethod]!![4] / 60
        }
        if (attribute.higherLatitudeMethod != HigherLatitudeMethod.NONE) {
            times = adjustHighLatTimes(times)
        }
        return times
    }

    // convert times array to given time format
    private fun adjustTimesFormat(times: DoubleArray): ArrayList<String> {
        val result = ArrayList<String>()
        for (i in 0..6) {
            result.add(floatToTime24(times[i]))
        }
        // remove sunset I don't need it in My Prayers app.
        result.removeAt(4)
        return result
    }

    // adjust Fajr, Isha and Maghrib for locations in higher latitudes
    private fun adjustHighLatTimes(times: DoubleArray): DoubleArray {
        val nightTime = timeDiff(times[4], times[1]) // sunset to sunrise

        // Adjust Fajr
        val fajrDiff =
            nightPortion(methodParams[attribute.calculationMethod]!![0]) * nightTime
        if (java.lang.Double.isNaN(times[0]) || timeDiff(
                times[0],
                times[1]
            ) > fajrDiff
        ) {
            times[0] = times[1] - fajrDiff
        }

        // Adjust Isha
        val ishaAngle: Double =
            if (methodParams[attribute.calculationMethod]!![3] == 0.0) methodParams[attribute.calculationMethod]!![4] else 18.0
        val ishaDiff = nightPortion(ishaAngle) * nightTime
        if (java.lang.Double.isNaN(times[6]) || timeDiff(
                times[4],
                times[6]
            ) > ishaDiff
        ) {
            times[6] = times[4] + ishaDiff
        }

        // Adjust Maghrib
        val maghribAngle: Double =
            if (methodParams[attribute.calculationMethod]!![1] == 0.0) methodParams[attribute.calculationMethod]!![2] else 4.0
        val maghribDiff = nightPortion(maghribAngle) * nightTime
        if (java.lang.Double.isNaN(times[5]) || timeDiff(
                times[4],
                times[5]
            ) > maghribDiff
        ) {
            times[5] = times[4] + maghribDiff
        }
        return times
    }

    // the night portion used for adjusting times in higher latitudes
    private fun nightPortion(angle: Double): Double {
        return when (attribute.higherLatitudeMethod) {
            HigherLatitudeMethod.ANGLE_BASED -> angle / 60.0
            HigherLatitudeMethod.MID_NIGHT -> 0.5
            HigherLatitudeMethod.ONE_SEVEN -> 0.14286
            HigherLatitudeMethod.NONE -> 0.0
        }
    }

    // convert hours to day portions
    private fun dayPortion(times: DoubleArray): DoubleArray {
        for (i in times.indices) {
            times[i] /= 24.0
        }
        return times
    }
}