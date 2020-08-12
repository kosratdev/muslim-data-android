package com.kosratahmed.muslimdata.database

import android.content.Context
import androidx.room.*
import com.kosratahmed.muslimdata.models.*
import com.kosratahmed.muslimdata.models.azkars.*
import com.kosratahmed.muslimdata.models.prayertime.FixedPrayerTime

/**
 * Data access object interface class for MuslimData database that holds queries which is
 * automatically will be used in Android Room library.
 */
@Dao
internal interface MuslimDataDao {
    /**
     * Search for cities in the database that match or like the provided city name.
     */
    @Transaction
    @Query("SELECT * FROM city WHERE city_name LIKE :city")
    fun searchCity(city: String): List<CountryAndCity>

    /**
     * Get user's location information based on the provided country code and city name in the
     * database.
     */
    @Transaction
    @Query("SELECT * FROM city WHERE country_code = :countryCode COLLATE NOCASE and city_name = :city COLLATE NOCASE")
    fun geoCoder(countryCode: String, city: String): CountryAndCity

    /**
     * Get user's location information based on the provided latitude and longitude in the database.
     */
    @Transaction
    @Query("SELECT * FROM city ORDER BY abs(latitude - :latitude) + abs(longitude - :longitude) LIMIT 1")
    fun geoCoder(latitude: Double, longitude: Double): CountryAndCity

    /**
     * Get prayer times for the specified user's location and date in the database.
     */
    @Query("SELECT * FROM prayer_time WHERE city = :city AND country_code = :countryCode AND date = :date")
    fun getPrayerTimes(countryCode: String, city: String, date: String): FixedPrayerTime

    /**
     * Get names of allah from the database for the specified language.
     */
    @Transaction
    @Query("SELECT * FROM name_translation WHERE language = :language")
    fun getNames(language: String): List<NameWithTranslation>

    /**
     * Get azkar categories from the database for the specified language.
     */
    @Transaction
    @Query("SELECT * FROM azkar_category_translation WHERE language = :language")
    fun getAzkarCategories(language: String): List<AzkarCategoryWithTranslation>

    /**
     * Get azkar chapters from the database for the specified language.
     */
    @Transaction
    @Query("SELECT * FROM azkar_chapter_translation WHERE language = :language")
    fun getAzkarChapters(language: String): List<AzkarChapterWithTranslation>

    /**
     * Get azkar items from the database for the specified chapter id and language.
     */
    @Transaction
    @Query("SELECT * FROM azkar_item_view WHERE chapterId = :chapterId AND language = :language")
    fun getAzkarItems(chapterId: Int, language: String): List<AzkarItemView>
}

/**
 * Create Room database instance from asset (prepared database).
 */
@Database(
    entities = [
        Country::class,
        City::class,
        FixedPrayerTime::class,
        Name::class,
        NameTranslation::class,
        AzkarCategoryDB::class,
        AzkarCategoryTranslation::class,
        AzkarChapterDB::class,
        AzkarChapterTranslation::class,
        AzkarItemDB::class,
        AzkarItemTranslation::class,
        AzkarReference::class,
        AzkarReferenceTranslation::class
    ],
    views = [AzkarItemView::class],
    version = 1
)
abstract class MuslimDataDatabase : RoomDatabase() {
    internal abstract val muslimDataDao: MuslimDataDao

    companion object {

        @Volatile
        private var INSTANCE: MuslimDataDatabase? = null

        fun getInstance(context: Context): MuslimDataDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, MuslimDataDatabase::class.java,
                        "muslim_db.db"
                    )
                        .createFromAsset("database/muslim_db_v1.0.0.db")
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
