package com.kosratahmed.muslimdata.repository

import android.content.Context
import com.kosratahmed.muslimdata.database.MuslimDataDatabase
import com.kosratahmed.muslimdata.extensions.formatToDBDate
import com.kosratahmed.muslimdata.extensions.toDate
import com.kosratahmed.muslimdata.models.NameOfAllah
import com.kosratahmed.muslimdata.models.UserLocation
import com.kosratahmed.muslimdata.models.azkars.AzkarCategory
import com.kosratahmed.muslimdata.models.azkars.AzkarChapter
import com.kosratahmed.muslimdata.models.azkars.AzkarItem
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
    suspend fun searchCity(city: String) = withContext(Dispatchers.IO) {
        UserLocation.mapDBLocations(muslimDb.muslimDataDao.searchCity("$city%"))
    }

    /**
     * Geocoding user's location information based on the provided country code and city name.
     */
    suspend fun geoCoder(countryCode: String, city: String) = withContext(Dispatchers.IO) {
        UserLocation.mapDBLocation(muslimDb.muslimDataDao.geoCoder(countryCode, city))
    }

    /**
     * Reverse geocoding user's location information based on the provided latitude and longitude.
     */
    suspend fun reverseGeoCoder(latitude: Double, longitude: Double) = withContext(Dispatchers.IO) {
        UserLocation.mapDBLocation(muslimDb.muslimDataDao.geoCoder(latitude, longitude))
    }

    /**
     * Get prayer times for the specified location, date, and prayer attribute.
     */
    suspend fun getPrayerTimes(
        location: UserLocation,
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
        NameOfAllah.mapDBNames(muslimDb.muslimDataDao.getNames(language))
    }

    /**
     * Get azkar categories for the specified language.
     */
    suspend fun getAzkarCategories(language: String) = withContext(Dispatchers.IO) {
        AzkarCategory.mapDBAzkarCategories(muslimDb.muslimDataDao.getAzkarCategories(language))
    }

    /**
     * Get azkar chapters for the specified language.
     */
    suspend fun getAzkarChapters(language: String) = withContext(Dispatchers.IO) {
        AzkarChapter.mapDBAzkarChapters(muslimDb.muslimDataDao.getAzkarChapters(language))
    }

    /**
     * Get azkar items for the specified azkar chapter id and language.
     */
    suspend fun getAzkarItems(chapterId: Int, language: String) = withContext(Dispatchers.IO) {
        AzkarItem.mapDBAzkarItems(muslimDb.muslimDataDao.getAzkarItems(chapterId, language))
    }
}