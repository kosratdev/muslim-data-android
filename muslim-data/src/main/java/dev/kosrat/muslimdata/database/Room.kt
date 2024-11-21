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
import dev.kosrat.muslimdata.extensions.PrefKeys
import dev.kosrat.muslimdata.extensions.get
import dev.kosrat.muslimdata.extensions.put
import dev.kosrat.muslimdata.extensions.sharedPreferences
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
        "SELECT location._id AS id, country.code AS countryCode, country.name AS countryName, " +
                "location.name AS name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country ON country._id = location.country_id " +
                "WHERE location.name like :locationName"
    )
    suspend fun searchLocation(locationName: String): List<Location>?

    /**
     * Get location information based on the provided country code and location name in the database.
     */
    @Transaction
    @Query(
        "SELECT location._id AS id, country.code AS countryCode, country.name AS countryName, " +
                "location.name AS name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country ON country._id = location.country_id " +
                "WHERE country.code= :countryCode  COLLATE NOCASE " +
                "AND location.name= :locationName COLLATE NOCASE"
    )
    suspend fun geocoder(countryCode: String, locationName: String): Location?

    /**
     * Get location information based on the provided latitude and longitude in the database.
     */
    @Transaction
    @Query(
        "SELECT location._id AS id, country.code AS countryCode, country.name AS countryName, " +
                "location.name AS name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country ON country._id = location.country_id " +
                "ORDER BY abs(latitude - :latitude) + abs(longitude - :longitude) " +
                "LIMIT 1"
    )
    suspend fun reverseGeocoder(latitude: Double, longitude: Double): Location?

    /**
     * Get prayer times for the specified user's location and date in the database.
     */
    @Query("SELECT * FROM prayer_time WHERE location_id = :locationId AND date = :date")
    suspend fun getPrayerTimes(locationId: Int, date: String): FixedPrayerTime

    /**
     * Get names of allah from the database for the specified language.
     */
    @Transaction
    @Query(
        "SELECT name._id AS number, name.name , transl.name AS translation " +
                "FROM name " +
                "INNER JOIN name_translation AS transl ON transl.name_id = name._id " +
                "AND transl.language = :language"
    )
    suspend fun getNames(language: String): List<NameOfAllah>

    /**
     * Get azkar categories from the database for the specified language.
     */
    @Transaction
    @Query(
        "SELECT category._id AS categoryId, category_name AS categoryName " +
                "FROM azkar_category AS category " +
                "INNER JOIN azkar_category_translation AS transl ON transl.category_id = category._id " +
                "WHERE language = :language"
    )
    suspend fun getAzkarCategories(language: String): List<AzkarCategory>

    /**
     * Get azkar chapters from the database for the specified language and category id.
     */
    @Transaction
    @Query(
        "SELECT chapter._id AS chapterId, category_id AS categoryId, " +
                "chapter_name AS chapterName " +
                "FROM azkar_chapter AS chapter " +
                "INNER JOIN azkar_chapter_translation AS transl ON transl.chapter_id = chapter._id " +
                "WHERE language = :language AND (:categoryId = -1 OR category_id = :categoryId)"
    )
    suspend fun getAzkarChapters(language: String, categoryId: Int): List<AzkarChapter>?

    /**
     * Get azkar chapters from the database for the specified language and chapter ids.
     */
    @Transaction
    @Query(
        "SELECT chapter._id AS chapterId, category_id AS categoryId, " +
                "chapter_name AS chapterName " +
                "FROM azkar_chapter AS chapter " +
                "INNER JOIN azkar_chapter_translation AS transl ON transl.chapter_id = chapter._id " +
                "WHERE language = :language AND chapter._id IN (:chapterIds)"
    )
    suspend fun getAzkarChapters(language: String, chapterIds: Array<Int>): List<AzkarChapter>?

    /**
     * Get azkar items from the database for the specified chapter id and language.
     */
    @Transaction
    @Query(
        "SELECT item._id AS itemId, item.chapter_id AS chapterId, item.item, " +
                "transl.item_translation AS translation, ref_transl.reference " +
                "FROM azkar_item AS item " +
                "INNER JOIN azkar_item_translation AS transl ON transl.item_id = item._id " +
                "INNER JOIN azkar_reference AS ref ON ref.item_id = item._id " +
                "INNER JOIN azkar_reference_translation AS ref_transl ON ref_transl.reference_id = ref._id AND " +
                "ref_transl.language = transl.language " +
                "WHERE chapterId = :chapterId AND transl.language = :language"
    )
    suspend fun getAzkarItems(chapterId: Int, language: String): List<AzkarItem>?

    @Transaction
    @Query(
        "SELECT location._id AS id, country.code AS countryCode, country.name AS countryName, " +
                "location.name AS name, latitude, longitude, " +
                "has_fixed_prayer_time AS hasFixedPrayerTime, " +
                "prayer_dependent_id AS prayerDependentId " +
                "FROM location " +
                "INNER JOIN country ON country._id = location.country_id " +
                "WHERE has_fixed_prayer_time = 1"
    )
    fun fixedPrayerTimesList(): List<Location>
}

/**
 * Version of the MuslimData database.
 */
private const val DB_VERSION = 21

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
    version = DB_VERSION,
    exportSchema = false
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
                    val dbName = "muslim_db.db"
                    val dbBuilder = Room.databaseBuilder(
                        context.applicationContext,
                        MuslimDataDatabase::class.java,
                        dbName
                    ).fallbackToDestructiveMigration()

                    // Copy database if it doesn't exist or the app version changes
                    val dbFile = context.getDatabasePath(dbName)
                    if (!dbFile.exists() || isVersionChanged(context)) {
                        dbBuilder.createFromAsset("database/muslim_db_v2.3.0.db")
                    }

                    instance = dbBuilder.build()

                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun isVersionChanged(context: Context): Boolean {
            // Implement logic to check if the database version has changed. The default value
            // should be 18 as we implemented this feature in the room database version 18.
            val currentVersion = context.sharedPreferences.get(PrefKeys.DB_VERSION, 18)
            if (currentVersion < DB_VERSION) {
                context.sharedPreferences.put(PrefKeys.DB_VERSION, DB_VERSION)
                return true
            }
            return false
        }
    }
}
