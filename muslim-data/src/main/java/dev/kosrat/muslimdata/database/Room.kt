package dev.kosrat.muslimdata.database

import android.content.Context
import androidx.room.*
import dev.kosrat.muslimdata.database.tables.CityTable
import dev.kosrat.muslimdata.database.tables.CountryTable
import dev.kosrat.muslimdata.database.tables.NameTable
import dev.kosrat.muslimdata.database.tables.NameTranslationTable
import dev.kosrat.muslimdata.database.tables.azkars.*
import dev.kosrat.muslimdata.database.tables.prayertimes.FixedPrayerTime
import dev.kosrat.muslimdata.models.*

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
    @Query(
        "SELECT city.country_code as countryCode, country_name as countryName, " +
                "city_name as cityName, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime " +
                "FROM city " +
                "INNER JOIN country on city.country_code = country.country_code " +
                "WHERE city_name like :city"
    )
    fun searchLocation(city: String): List<Location>?

    /**
     * Get location information based on the provided country code and city name in the database.
     */
    @Transaction
    @Query(
        "SELECT city.country_code as countryCode, country_name as countryName, " +
                "city_name as cityName, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime " +
                "FROM city " +
                "INNER JOIN country on city.country_code = country.country_code " +
                "WHERE city.country_code= :countryCode  COLLATE NOCASE " +
                "and city_name= :city COLLATE NOCASE"
    )
    fun geocoder(countryCode: String, city: String): Location?

    /**
     * Get location information based on the provided latitude and longitude in the database.
     */
    @Transaction
    @Query(
        "SELECT city.country_code as countryCode, country_name as countryName, " +
                "city_name as cityName, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime " +
                "FROM city " +
                "INNER JOIN country on city.country_code = country.country_code " +
                "ORDER BY abs(latitude - :latitude) + abs(longitude - :longitude) LIMIT 1"
    )
    fun reverseGeocoder(latitude: Double, longitude: Double): Location?

    /**
     * Get prayer times for the specified user's location and date in the database.
     */
    @Query("SELECT * FROM prayer_time WHERE city = :city AND country_code = :countryCode AND date = :date")
    fun getPrayerTimes(countryCode: String, city: String, date: String): FixedPrayerTime

    /**
     * Get names of allah from the database for the specified language.
     */
    @Transaction
    @Query(
        "SELECT name._id AS number, name.name , tr.name AS translation " +
                "FROM name " +
                "INNER JOIN name_translation as tr on tr.name_id = name._id " +
                "and tr.language = :language"
    )
    fun getNames(language: String): List<NameOfAllah>

    /**
     * Get azkar categories from the database for the specified language.
     */
    @Transaction
    @Query(
        "SELECT category._id AS categoryId, category_name AS categoryName " +
                "FROM azkar_category AS category " +
                "INNER JOIN azkar_category_translation as tr on tr.category_id = category._id " +
                "WHERE language = :language"
    )
    fun getAzkarCategories(language: String): List<AzkarCategory>

    /**
     * Get azkar chapters from the database for the specified language.
     */
    @Transaction
    @Query(
        "SELECT chapter._id AS chapterId, category_id AS categoryId, " +
                "chapter_name AS chapterName " +
                "FROM azkar_chapter AS chapter " +
                "INNER JOIN azkar_chapter_translation as tr on tr.chapter_id = chapter._id " +
                "WHERE language = :language and (:categoryId = -1 OR category_id = :categoryId)"
    )
    fun getAzkarChapters(language: String, categoryId: Int): List<AzkarChapter>?

    /**
     * Get azkar items from the database for the specified chapter id and language.
     */
    @Transaction
    @Query(
        "SELECT item._id AS itemId, item.chapter_id AS chapterId, tr.language, item.item, " +
                "tr.item_translation AS translation, rtr.reference " +
                "FROM azkar_item as item " +
                "INNER JOIN azkar_item_translation AS tr ON tr.item_id = item._id " +
                "INNER JOIN azkar_reference AS ref ON ref.item_id = item._id " +
                "INNER JOIN azkar_reference_translation AS rtr ON rtr.reference_id = ref._id AND " +
                "rtr.language = tr.language " +
                "WHERE chapterId = :chapterId AND tr.language = :language"
    )
    fun getAzkarItems(chapterId: Int, language: String): List<AzkarItem>?
}

/**
 * Create Room database instance from asset (prepared database).
 */
@Database(
    entities = [
        CountryTable::class,
        CityTable::class,
        FixedPrayerTime::class,
        NameTable::class,
        NameTranslationTable::class,
        AzkarCategoryTable::class,
        AzkarCategoryTranslationTable::class,
        AzkarChapterTable::class,
        AzkarChapterTranslationTable::class,
        AzkarItemTable::class,
        AzkarItemTranslationTable::class,
        AzkarReferenceTable::class,
        AzkarReferenceTranslationTable::class
    ],
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
