package dev.kosrat.muslimdata.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import dev.kosrat.muslimdata.database.tables.CountryTable
import dev.kosrat.muslimdata.database.tables.LocationTable
import dev.kosrat.muslimdata.database.tables.NameTable
import dev.kosrat.muslimdata.database.tables.NameTranslationTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarCategoryTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarCategoryTranslationTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarChapterTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarChapterTranslationTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarItemTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarItemTranslationTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarReferenceTable
import dev.kosrat.muslimdata.database.tables.azkars.AzkarReferenceTranslationTable
import dev.kosrat.muslimdata.database.tables.prayertimes.FixedPrayerTime
import dev.kosrat.muslimdata.models.AzkarCategory
import dev.kosrat.muslimdata.models.AzkarChapter
import dev.kosrat.muslimdata.models.AzkarItem
import dev.kosrat.muslimdata.models.Location
import dev.kosrat.muslimdata.models.NameOfAllah

/**
 * Data access object interface class for MuslimData database that holds queries which is
 * automatically will be used in Android Room library.
 */
@Dao
internal interface MuslimDataDao {
    /**
     * Search for locations in the database that match or like the provided name.
     */
    @Transaction
    @Query(
        "SELECT location._id as id, country.code as countryCode, country.name as countryName, " +
                "location.name as name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country on country._id = location.country_id " +
                "WHERE location.name like :locationName"
    )
    fun searchLocation(locationName: String): List<Location>?

    /**
     * Get location information based on the provided country code and location name in the database.
     */
    @Transaction
    @Query(
        "SELECT location._id as id, country.code as countryCode, country.name as countryName, " +
                "location.name as name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country on country._id = location.country_id " +
                "WHERE country.code= :countryCode  COLLATE NOCASE " +
                "and location.name= :locationName COLLATE NOCASE"
    )
    fun geocoder(countryCode: String, locationName: String): Location?

    /**
     * Get location information based on the provided latitude and longitude in the database.
     */
    @Transaction
    @Query(
        "SELECT location._id as id, country.code as countryCode, country.name as countryName, " +
                "location.name as name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country on country._id = location.country_id " +
                "ORDER BY abs(latitude - :latitude) + abs(longitude - :longitude) " +
                "LIMIT 1"
    )
    fun reverseGeocoder(latitude: Double, longitude: Double): Location?

    /**
     * Get prayer times for the specified user's location and date in the database.
     */
    @Query("SELECT * FROM prayer_time WHERE location_id = :locationId AND date = :date")
    fun getPrayerTimes(locationId: Int, date: String): FixedPrayerTime

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
     * Get azkar chapters from the database for the specified language and category id.
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
     * Get azkar chapters from the database for the specified language and chapter ids.
     */
    @Transaction
    @Query(
        "SELECT chapter._id AS chapterId, category_id AS categoryId, " +
                "chapter_name AS chapterName " +
                "FROM azkar_chapter AS chapter " +
                "INNER JOIN azkar_chapter_translation as tr on tr.chapter_id = chapter._id " +
                "WHERE language = :language and chapter._id in (:azkarIds)"
    )
    fun getAzkarChapters(language: String, azkarIds: Array<Int>): List<AzkarChapter>?

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

    @Transaction
    @Query(
        "SELECT location._id as id, country.code as countryCode, country.name as countryName, " +
                "location.name as name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country on country._id = location.country_id " +
                "WHERE has_fixed_prayer_time = 1"
    )
    fun fixedPrayerTimesList(): List<Location>
}

/**
 * Create Room database instance from asset (prepared database).
 */
@Database(
    entities = [
        CountryTable::class,
        LocationTable::class,
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
    version = 16
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
                        .createFromAsset("database/muslim_db_v2.0.0.db")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
