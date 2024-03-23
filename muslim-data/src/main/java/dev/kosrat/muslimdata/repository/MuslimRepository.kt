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

class MuslimRepository(context: Context) : Repository {
    private val muslimDb = MuslimDataDatabase.getInstance(context)

    /**
     * Search for locations in the database by location name and it will return a list of Location
     * object.
     */
    override suspend fun searchLocation(locationName: String): List<Location>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.searchLocation("$locationName%")
        }
    }

    /**
     * Geocoding location information based on the provided country code and location name.
     */
    override suspend fun geocoder(countryCode: String, locationName: String): Location? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.geocoder(countryCode, locationName)
        }
    }

    /**
     * Reverse geocoding location information based on the provided latitude and longitude.
     */
    override suspend fun reverseGeocoder(latitude: Double, longitude: Double): Location? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.reverseGeocoder(latitude, longitude)
        }
    }

    /**
     * Get prayer times for the specified location, date, and prayer attribute.
     */
    override suspend fun getPrayerTimes(
        location: Location, date: Date, attribute: PrayerAttribute
    ): PrayerTime {
        return withContext(Dispatchers.IO) {
            val prayerTime: PrayerTime
            if (location.hasFixedPrayerTime) {
                val fixedPrayer = muslimDb.muslimDataDao.getPrayerTimes(
                    location.prayerDependentId ?: location.id, date.formatToDBDate()
                )
                prayerTime = PrayerTime(
                    fixedPrayer.fajr.toDate(date),
                    fixedPrayer.sunrise.toDate(date),
                    fixedPrayer.dhuhr.toDate(date),
                    fixedPrayer.asr.toDate(date),
                    fixedPrayer.maghrib.toDate(date),
                    fixedPrayer.isha.toDate(date)
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
     * Get names of Allah for the specified language.
     */
    override suspend fun getNamesOfAllah(language: Language): List<NameOfAllah> {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getNames(language.value)
        }
    }

    /**
     * Get azkar categories for the specified language.
     */
    override suspend fun getAzkarCategories(language: Language): List<AzkarCategory> {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarCategories(language.value)
        }
    }

    /**
     * Get azkar chapters for the specified language and category id.
     */
    override suspend fun getAzkarChapters(
        language: Language, categoryId: Int
    ): List<AzkarChapter>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarChapters(language.value, categoryId)
        }
    }

    /**
     * Get azkar chapters for the specified language and chapter ids.
     */
    override suspend fun getAzkarChapters(
        language: Language, azkarIds: Array<Int>
    ): List<AzkarChapter>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarChapters(language.value, azkarIds)
        }
    }

    /**
     * Get azkar items for the specified azkar chapter id and language.
     */
    override suspend fun getAzkarItems(chapterId: Int, language: Language): List<AzkarItem>? {
        return withContext(Dispatchers.IO) {
            muslimDb.muslimDataDao.getAzkarItems(chapterId, language.value)
        }
    }
}