package com.kosratahmed.muslimdataexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kosratahmed.muslimdata.models.prayertime.*
import com.kosratahmed.muslimdata.repository.Repository
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lifecycleScope.launch {
            val search = Repository(this@MainActivity).searchCity("erb")
            Log.i("search", "$search")

            val geoCoder = Repository(this@MainActivity).geoCoder("iq", "erbil")
            Log.i("geoCoder", "$geoCoder")

            val geoCoderLocation = Repository(this@MainActivity).geoCoder(36.0901, 43.0930)
            Log.i("geoCoderLocation", "$geoCoderLocation")

            val attribute = PrayerAttribute(
                CalculationMethod.MAKKAH,
                AsrMethod.SHAFII,
                HigherLatitudeMethod.ANGLE_BASED,
                intArrayOf(1, 1, 1, 1, 1, 1)
            )
            val prayerTime = Repository(this@MainActivity).getPrayerTimes(
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
        }
    }
}