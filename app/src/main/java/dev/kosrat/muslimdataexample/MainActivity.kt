package dev.kosrat.muslimdataexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.kosrat.muslimdata.models.*
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val repository = MuslimRepository(this@MainActivity)
            val locationList = repository.searchLocation("erb")
            Log.i("searchLocation", "$locationList")

            val location = repository.geocoder("iq", "erbil")
            Log.i("geocoder", "$location")

            val location2 = repository.reverseGeocoder(36.0901, 43.0930)
            Log.i("reverseGeocoder", "$location2")

            val attribute = PrayerAttribute(
                CalculationMethod.MAKKAH,
                AsrMethod.SHAFII,
                HigherLatitudeMethod.ANGLE_BASED,
                intArrayOf(0, 0, 0, 0, 0, 0)
            )
            val prayerTime = repository.getPrayerTimes(
                location!!,
                Date(),
                attribute
            )
            Log.i("Prayer times ", "$prayerTime")
            Log.i(
                "formatPrayerTime ",
                prayerTime.formatPrayerTime(TimeFormat.TIME_12).contentToString()
            )
            Log.i("nextPrayerTimeIndex", "${prayerTime.nextPrayerTimeIndex()}")
            Log.i("nextPrayerTimeInterval", "${prayerTime.nextPrayerTimeInterval()}")
            Log.i("nextPrayerTimeRemaining", prayerTime.nextPrayerTimeRemaining())

            val azkarCategories = repository.getAzkarCategories(Language.EN)
            Log.i("azkarCategories", "$azkarCategories")

            val azkarChapters = repository.getAzkarChapters(1, Language.EN)
            Log.i("azkarChapters", "$azkarChapters")

            val azkarItems = repository.getAzkarItems(1, Language.EN)
            Log.i("azkarItems", "$azkarItems")

            val names = repository.getNamesOfAllah(Language.EN)
            Log.i("Names", "$names")
        }
    }
}