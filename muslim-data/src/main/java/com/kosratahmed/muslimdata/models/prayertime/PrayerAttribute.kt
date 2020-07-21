package com.kosratahmed.muslimdata.models.prayertime

data class PrayerAttribute(
    val calculationMethod: CalculationMethod,
    val asrMethod: AsrMethod,
    val higherLatitudeMethod: HigherLatitudeMethod,
    val timeFormat: TimeFormat,
    val offset: IntArray = IntArray(6){0}
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrayerAttribute

        if (calculationMethod != other.calculationMethod) return false
        if (asrMethod != other.asrMethod) return false
        if (higherLatitudeMethod != other.higherLatitudeMethod) return false
        if (timeFormat != other.timeFormat) return false
        if (!offset.contentEquals(other.offset)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = calculationMethod.hashCode()
        result = 31 * result + asrMethod.hashCode()
        result = 31 * result + higherLatitudeMethod.hashCode()
        result = 31 * result + timeFormat.hashCode()
        result = 31 * result + offset.contentHashCode()
        return result
    }
}

enum class CalculationMethod {
     MAKKAH, // Umm al-Qura, Makkah
     MWL, // Muslim World League (MWL)
     ISNA, // Islamic Society of North America (ISNA)
     KARACHI, // University of Islamic Sciences, Karachi
     EGYPT, // Egyptian General Authority of Survey
     JAFARI, // Ithna Ashari
     TEHRAN, // Institute of Geophysics, University of Tehran
     CUSTOM; // Custom Setting

    companion object {
        fun methodList(): HashMap<CalculationMethod, DoubleArray> {
            return hashMapOf(
                MAKKAH to doubleArrayOf(18.5, 1.0, 0.0, 1.0, 90.0),
                MWL to doubleArrayOf(18.0, 1.0, 0.0, 0.0, 17.0),
                ISNA to doubleArrayOf(15.0, 1.0, 0.0, 0.0, 15.0),
                KARACHI to doubleArrayOf(18.0, 1.0, 0.0, 0.0, 18.0),
                EGYPT to doubleArrayOf(19.5, 1.0, 0.0, 0.0, 17.5),
                JAFARI to doubleArrayOf(16.0, 0.0, 4.0, 0.0, 14.0),
                TEHRAN to doubleArrayOf(17.7, 0.0, 4.5, 0.0, 14.0),
                CUSTOM to doubleArrayOf(18.0, 1.0, 0.0, 0.0, 17.0)
            )
        }
    }
}

enum class AsrMethod(val value: Int) {
    SHAFII(0), // Shafii (standard)
    HANAFI(1), // Hanafi
}

enum class HigherLatitudeMethod {
     NONE, // No adjustment
     MID_NIGHT, // middle of night
     ONE_SEVEN, // 1/7th of night
     ANGLE_BASED // floating point number
}

enum class TimeFormat {
    TIME_24, // 24-hour format
    TIME_12, // 12-hour format
    TIME_12_NS, // 12-hour format with no suffix
    FLOATING // angle/60th of night
}