package com.kosratahmed.muslimdata.database

import android.content.Context
import androidx.room.*
import com.kosratahmed.muslimdata.models.*
import com.kosratahmed.muslimdata.models.azkars.*
import com.kosratahmed.muslimdata.models.prayertime.FixedPrayerTime

@Dao
internal interface MuslimDataDao {

    @Transaction
    @Query("SELECT * FROM city WHERE city_name LIKE :city")
    fun searchCity(city: String): List<CountryAndCity>

    @Transaction
    @Query("SELECT * FROM city WHERE country_code = :countryCode COLLATE NOCASE and city_name = :city COLLATE NOCASE")
    fun geoCoder(countryCode: String, city: String): CountryAndCity

    @Transaction
    @Query("SELECT * FROM city ORDER BY abs(latitude - :latitude) + abs(longitude - :longitude) LIMIT 1")
    fun geoCoder(latitude: Double, longitude: Double): CountryAndCity

    @Query("SELECT * FROM prayer_time WHERE city = :city AND country_code = :countryCode AND date = :date")
    fun getPrayerTimes(countryCode: String, city: String, date: String): FixedPrayerTime

    @Transaction
    @Query("SELECT * FROM name_translation WHERE language = :language")
    fun getNames(language: String): List<NameWithTranslation>

    @Transaction
    @Query("SELECT * FROM azkar_category_translation WHERE language = :language")
    fun getAzkarCategories(language: String): List<AzkarCategoryWithTranslation>

    @Transaction
    @Query("SELECT * FROM azkar_chapter_translation WHERE language = :language")
    fun getAzkarChapters(language: String): List<AzkarChapterWithTranslation>

    @Transaction
    @Query("SELECT * FROM azkar_view WHERE chapterId = :chapterId AND language = :language")
    fun getAzkarItems(chapterId: Int, language: String): List<AzkarItemDetail>
}

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
    views = [AzkarItemDetail::class],
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
