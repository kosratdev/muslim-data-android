package dev.kosrat.muslimdata.repository

import dev.kosrat.muslimdata.models.AzkarCategory
import dev.kosrat.muslimdata.models.AzkarChapter
import dev.kosrat.muslimdata.models.AzkarItem
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.models.Location
import dev.kosrat.muslimdata.models.NameOfAllah
import dev.kosrat.muslimdata.models.PrayerAttribute
import dev.kosrat.muslimdata.models.PrayerTime
import java.util.Date

interface Repository {

    suspend fun searchLocation(locationName: String): List<Location>?

    suspend fun geocoder(countryCode: String, locationName: String): Location?

    suspend fun reverseGeocoder(latitude: Double, longitude: Double): Location?

    suspend fun getPrayerTimes(
        location: Location,
        date: Date,
        attribute: PrayerAttribute
    ): PrayerTime

    suspend fun getNamesOfAllah(language: Language): List<NameOfAllah>

    suspend fun getAzkarCategories(language: Language): List<AzkarCategory>

    suspend fun getAzkarChapters(language: Language, categoryId: Int = -1): List<AzkarChapter>?

    suspend fun getAzkarChapters(language: Language, chapterIds: Array<Int>): List<AzkarChapter>?

    suspend fun getAzkarItems(chapterId: Int, language: Language): List<AzkarItem>?
}