package com.kosratahmed.muslimdata.database

import android.content.Context
import androidx.room.*
import com.kosratahmed.muslimdata.models.PrayerTime

@Dao
interface MuslimDataDao {

    @Query("SELECT * FROM prayer_time WHERE city = :city")
    fun getPrayerTimes(city: String): List<PrayerTime>
}

@Database(entities = [PrayerTime::class], version = 1)
abstract class MuslimDataDatabase : RoomDatabase() {
    abstract val muslimDataDao: MuslimDataDao

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
