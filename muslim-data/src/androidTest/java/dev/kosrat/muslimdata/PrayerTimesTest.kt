package dev.kosrat.muslimdata

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.database.MuslimDataDao
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import dev.kosrat.muslimdata.models.AsrMethod.SHAFII
import dev.kosrat.muslimdata.models.CalculationMethod.MAKKAH
import dev.kosrat.muslimdata.models.HigherLatitudeMethod.ANGLE_BASED
import dev.kosrat.muslimdata.models.Location
import dev.kosrat.muslimdata.models.PrayerAttribute
import dev.kosrat.muslimdata.models.TimeFormat
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class PrayerTimesTest {

    private lateinit var context: Context
    private lateinit var muslimDataDatabase: MuslimDataDatabase
    private lateinit var muslimDataDao: MuslimDataDao
    private lateinit var attributes: PrayerAttribute

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        muslimDataDatabase = Room.databaseBuilder(
            context.applicationContext, MuslimDataDatabase::class.java, "muslim_db.db"
        ).createFromAsset("database/muslim_db_v2.0.0.db").fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

        muslimDataDao = muslimDataDatabase.muslimDataDao

        attributes = PrayerAttribute(MAKKAH, SHAFII, ANGLE_BASED, intArrayOf(1, 1, 1, -1, -1, -1))
    }

    @After
    fun teardown() {
        muslimDataDatabase.close()
    }

    @Test
    fun fixedPrayerTimes_allLocations_isNotNull() = runBlocking {
        val locations = muslimDataDao.fixedPrayerTimesList()
        Assert.assertNotNull(locations)

        val date = Date(1709206718000)
        locations.forEach { location ->
            val prayerTime = MuslimRepository(context).getPrayerTimes(location, date, attributes)
            Assert.assertNotNull(prayerTime)
        }
    }

    @Test
    fun calculatedPrayerTimes_meccaPrayer_isCorrect() = runBlocking {
        val makkah =
            Location(165009, "Makkah", 21.419828, 39.826898, "SA", "Saudi Arabia", false, null)
        val date = Date(1538956800000)
        val prayer = MuslimRepository(context).getPrayerTimes(makkah, date, attributes)

        Assert.assertNotNull(prayer)
        val stringPrayer = prayer.formatPrayerTime(TimeFormat.TIME_24)
        Assert.assertEquals(stringPrayer[0], "04:59")
        Assert.assertEquals(stringPrayer[1], "06:15")
        Assert.assertEquals(stringPrayer[2], "12:09")
        Assert.assertEquals(stringPrayer[3], "15:30")
        Assert.assertEquals(stringPrayer[4], "18:01")
        Assert.assertEquals(stringPrayer[5], "19:31")
    }
}