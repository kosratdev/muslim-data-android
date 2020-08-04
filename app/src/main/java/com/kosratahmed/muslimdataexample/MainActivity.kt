package com.kosratahmed.muslimdataexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kosratahmed.muslimdata.models.prayertime.*
import com.kosratahmed.muslimdata.repository.Repository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lifecycleScope.launch {
            val repository = Repository(this@MainActivity)
            val search = repository.searchCity("erb")
            Log.i("search", "$search")

            val geoCoder = repository.geoCoder("iq", "erbil")
            Log.i("geoCoder", "$geoCoder")

            val geoCoderLocation = repository.geoCoder(36.0901, 43.0930)
            Log.i("geoCoderLocation", "$geoCoderLocation")

            val attribute = PrayerAttribute(
                CalculationMethod.MAKKAH,
                AsrMethod.SHAFII,
                HigherLatitudeMethod.ANGLE_BASED,
                intArrayOf(1, 1, 1, 1, 1, 1)
            )
            val prayerTime = repository.getPrayerTimes(
                geoCoder.city,
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

            val names = repository.getNames("en")
            Log.i("Names", "$names")
        }
    }
}