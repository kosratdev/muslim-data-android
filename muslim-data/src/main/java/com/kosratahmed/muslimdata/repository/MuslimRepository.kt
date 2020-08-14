package com.kosratahmed.muslimdata.repository

import android.content.Context
import com.kosratahmed.muslimdata.database.MuslimDataDatabase
import com.kosratahmed.muslimdata.extensions.formatToDBDate
import com.kosratahmed.muslimdata.extensions.toDate
import com.kosratahmed.muslimdata.models.Location
import com.kosratahmed.muslimdata.models.prayertime.CalculatedPrayerTime
import com.kosratahmed.muslimdata.models.prayertime.PrayerAttribute
import com.kosratahmed.muslimdata.models.prayertime.PrayerTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MuslimRepository(context: Context) {
    private val muslimDb = MuslimDataDatabase.getInstance(context)

    /**
     * Search for cities in the database by city name and it will return a list of UserLocation
     * object.
     */
    suspend fun searchLocation(city: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.searchLocation("$city%")
    }

    /**
     * Geocoding location information based on the provided country code and city name.
     */
    suspend fun geocoder(countryCode: String, city: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.geocoder(countryCode, city)
    }

    /**
     * Reverse geocoding location information based on the provided latitude and longitude.
     */
    suspend fun reverseGeocoder(latitude: Double, longitude: Double) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.reverseGeocoder(latitude, longitude)
    }

    /**
     * Get prayer times for the specified location, date, and prayer attribute.
     */
    suspend fun getPrayerTimes(
        location: Location,
        date: Date,
        attribute: PrayerAttribute
    ) = withContext(Dispatchers.IO) {
        val prayerTime: PrayerTime
        if (location.hasFixedPrayerTime) {
            val fixedPrayer = muslimDb.muslimDataDao.getPrayerTimes(
                location.countryCode,
                location.cityName,
                date.formatToDBDate()
            )
            prayerTime = PrayerTime(
                fixedPrayer.fajr.toDate(),
                fixedPrayer.sunrise.toDate(),
                fixedPrayer.dhuhr.toDate(),
                fixedPrayer.asr.toDate(),
                fixedPrayer.maghrib.toDate(),
                fixedPrayer.isha.toDate()
            )
            prayerTime.adjustDST()
        } else {
            prayerTime = CalculatedPrayerTime(attribute).getPrayerTimes(location, date)
        }
        prayerTime.applyOffset(attribute.offset)
        prayerTime
    }

    /**
     * Get names of allah for the specified language.
     */
    suspend fun getNamesOfAllah(language: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.getNames(language)
    }

    /**
     * Get azkar categories for the specified language.
     */
    suspend fun getAzkarCategories(language: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.getAzkarCategories(language)
    }

    /**
     * Get azkar chapters for the specified language.
     */
    suspend fun getAzkarChapters(language: String, categoryId: Long = -1) =
        withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarChapters(language, categoryId)
        }

    /**
     * Get azkar items for the specified azkar chapter id and language.
     */
    suspend fun getAzkarItems(chapterId: Int, language: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.getAzkarItems(chapterId, language)
    }
}