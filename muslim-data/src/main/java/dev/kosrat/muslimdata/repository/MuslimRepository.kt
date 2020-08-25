package dev.kosrat.muslimdata.repository

import android.content.Context
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import dev.kosrat.muslimdata.database.tables.prayertimes.CalculatedPrayerTime
import dev.kosrat.muslimdata.extensions.formatToDBDate
import dev.kosrat.muslimdata.extensions.toDate
import dev.kosrat.muslimdata.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MuslimRepository(context: Context) {
    private val muslimDb = MuslimDataDatabase.getInstance(context)

    /**
     * Search for cities in the database by city name and it will return a list of UserLocation
     * object.
     */
    suspend fun searchLocation(city: String): List<Location>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.searchLocation("$city%")
        }
    }

    /**
     * Geocoding location information based on the provided country code and city name.
     */
    suspend fun geocoder(countryCode: String, city: String): Location? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.geocoder(countryCode, city)
        }
    }

    /**
     * Reverse geocoding location information based on the provided latitude and longitude.
     */
    suspend fun reverseGeocoder(latitude: Double, longitude: Double): Location? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.reverseGeocoder(latitude, longitude)
        }
    }

    /**
     * Get prayer times for the specified location, date, and prayer attribute.
     */
    suspend fun getPrayerTimes(
        location: Location,
        date: Date,
        attribute: PrayerAttribute
    ): PrayerTime {
        return withContext(Dispatchers.IO) {
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
    }

    /**
     * Get names of allah for the specified language.
     */
    suspend fun getNamesOfAllah(language: Language): List<NameOfAllah> {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getNames(language.value)
        }
    }

    /**
     * Get azkar categories for the specified language.
     */
    suspend fun getAzkarCategories(language: Language): List<AzkarCategory> {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarCategories(language.value)
        }
    }

    /**
     * Get azkar chapters for the specified language.
     */
    suspend fun getAzkarChapters(categoryId: Long = -1, language: Language): List<AzkarChapter>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarChapters(language.value, categoryId)
        }
    }

    /**
     * Get azkar items for the specified azkar chapter id and language.
     */
    suspend fun getAzkarItems(chapterId: Int, language: Language): List<AzkarItem>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarItems(chapterId, language.value)
        }
    }
}