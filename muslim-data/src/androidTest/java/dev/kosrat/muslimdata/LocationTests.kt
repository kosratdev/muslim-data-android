package dev.kosrat.muslimdata

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.database.MuslimDataDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationTests {

    private lateinit var context: Context
    private lateinit var muslimDataDatabase: MuslimDataDatabase
    private lateinit var muslimDataDao: MuslimDataDao

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        muslimDataDatabase = Room.databaseBuilder(
            context.applicationContext,
            MuslimDataDatabase::class.java,
            "muslim_db.db"
        )
            .createFromAsset("database/muslim_db_v2.0.0.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        muslimDataDao = muslimDataDatabase.muslimDataDao
    }

    @After
    fun teardown() {
        muslimDataDatabase.close()
    }

    @Test
    fun testGeocoderName() {
        // Test (London, GB) which has fixed prayer times.
        muslimDataDao.geocoder(countryCode = "GB",  "London").let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.latitude, 51.50853, 0.0001)
                Assert.assertEquals(location.longitude, -0.12574, 0.0001)
                Assert.assertEquals(location.hasFixedPrayerTime, true)
            }
        }

        // Test Nil.
        muslimDataDao.geocoder(countryCode = "abc", "Unknown").let { location ->
            Assert.assertNull(location)
        }

        // Test (Tahran, IR) which hasn't fixed prayer times.
        muslimDataDao.geocoder(countryCode = "IR", "Tehran").let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.latitude, 35.69439, 0.0001)
                Assert.assertEquals(location.longitude, 51.42151, 0.0001)
                Assert.assertEquals(location.hasFixedPrayerTime, false)
            }
        }

        // Test (Soran, IQ) which has fixed prayer times by  mapper.
        muslimDataDao.geocoder(countryCode = "IQ", "Soran").let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.latitude, 36.652686, 0.0001)
                Assert.assertEquals(location.longitude, 44.541427, 0.0001)
                Assert.assertEquals(location.hasFixedPrayerTime, true)
            }
        }

        // Test (Qasre, IQ) which has fixed prayer times by  mapper.
        muslimDataDao.geocoder(countryCode = "IQ", "Qasre").let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.latitude, 36.557804, 0.0001)
                Assert.assertEquals(location.longitude, 44.827805, 0.0001)
                Assert.assertEquals(location.hasFixedPrayerTime, true)
            }
        }
    }

    @Test
    fun testGeocoderLocation() {
        // Test (London, GB) which has fixed prayer times.
        muslimDataDao.reverseGeocoder(latitude = 51.50853, longitude = -0.12574).let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.name, "London")
                Assert.assertEquals(location.countryCode, "GB")
                Assert.assertEquals(location.countryName, "United Kingdom")
                Assert.assertEquals(location.hasFixedPrayerTime, true)
            }
        }

        // Test (Tahran, IR) which hasn't fixed prayer times.
        muslimDataDao.reverseGeocoder(latitude = 35.69439, longitude = 51.42151).let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.name, "Tehran")
                Assert.assertEquals(location.countryCode, "IR")
                Assert.assertEquals(location.countryName, "Iran")
                Assert.assertEquals(location.hasFixedPrayerTime, false)
            }
        }

        // Test (Soran, IQ) which has fixed prayer times by  mapper.
        muslimDataDao.reverseGeocoder(latitude = 36.652686, longitude = 44.541427).let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.name, "Soran")
                Assert.assertEquals(location.countryCode, "IQ")
                Assert.assertEquals(location.countryName, "Iraq")
                Assert.assertEquals(location.hasFixedPrayerTime, true)
            }
        }

        // Test (Qasre, IQ) which has fixed prayer times by  mapper.
        muslimDataDao.reverseGeocoder(latitude = 36.557804, longitude = 44.827805).let { location ->
            Assert.assertNotNull(location)
            if (location != null) {
                Assert.assertEquals(location.name, "Qasre")
                Assert.assertEquals(location.countryCode, "IQ")
                Assert.assertEquals(location.countryName, "Iraq")
                Assert.assertEquals(location.hasFixedPrayerTime, true)
            }
        }
    }
}
