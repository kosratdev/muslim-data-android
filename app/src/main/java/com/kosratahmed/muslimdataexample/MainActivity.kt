package com.kosratahmed.muslimdataexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kosratahmed.muslimdata.models.prayertime.*
import com.kosratahmed.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val repository = MuslimRepository(this@MainActivity)
            val search = repository.searchCity("erb")
            Log.i("search", "$search")

            val geoCoder = repository.reverseGeoCoder("iq", "erbil")
            Log.i("geoCoder", "$geoCoder")

            val geoCoderLocation = repository.reverseGeoCoder(36.0901, 43.0930)
            Log.i("geoCoderLocation", "$geoCoderLocation")

            val attribute = PrayerAttribute(
                CalculationMethod.MAKKAH,
                AsrMethod.SHAFII,
                HigherLatitudeMethod.ANGLE_BASED,
                intArrayOf(1, 1, 1, 1, 1, 1)
            )
            val prayerTime = repository.getPrayerTimes(
                geoCoder,
                Date(),
                attribute
            )
            Log.i("Prayer times ", "$prayerTime")
            Log.i(
                "Prayer times format ",
                prayerTime.formatPrayerTime(TimeFormat.TIME_12).contentToString()
            )
            Log.i("Next prayer index", "${prayerTime.nextPrayerTimeIndex()}")
            Log.i("Next prayer interval", "${prayerTime.nextPrayerTimeInterval()}")
            Log.i("Next prayer remaining", prayerTime.nextPrayerTimeRemaining())

            val names = repository.getNamesOfAllah("en")
            Log.i("Names", "$names")

            val azkarCategory = repository.getAzkarCategories("en")
            Log.i("azkar category", "$azkarCategory")

            val azkarChapters = repository.getAzkarChapters("en")
            Log.i("azkar chapters", "$azkarChapters")

            val azkarItems = repository.getAzkarItems(1, "en")
            Log.i("azkar items", "$azkarItems")
        }
    }
}