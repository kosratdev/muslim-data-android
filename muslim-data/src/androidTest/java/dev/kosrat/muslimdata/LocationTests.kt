package dev.kosrat.muslimdata

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationTests {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun teardown() {
    }

    @Test
    fun geocoder_londonGB_isCorrect() = runBlocking {
        // Test (London, GB) which has fixed prayer times.
        val location = MuslimRepository(context).geocoder("GB", "London")
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.latitude, 51.50853, 0.0001)
        Assert.assertEquals(location.longitude, -0.12574, 0.0001)
        Assert.assertEquals(location.hasFixedPrayerTime, true)
    }

    @Test
    fun geocoder_unknownCity_isNull() = runBlocking {
        // Test Null.
        val location = MuslimRepository(context).geocoder("abc", "Unknown")
        Assert.assertNull(location)
    }

    @Test
    fun geocoder_tehranIR_isCorrect() = runBlocking {
        // Test (Tehran, IR) which hasn't fixed prayer times.
        val location = MuslimRepository(context).geocoder("IR", "Tehran")
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.latitude, 35.69439, 0.0001)
        Assert.assertEquals(location.longitude, 51.42151, 0.0001)
        Assert.assertEquals(location.hasFixedPrayerTime, false)
    }

    @Test
    fun geocoder_soranIQ_isCorrect() = runBlocking {
        // Test (Soran, IQ) which has fixed prayer times by  mapper.
        val location = MuslimRepository(context).geocoder("IQ", "Soran")
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.latitude, 36.652686, 0.0001)
        Assert.assertEquals(location.longitude, 44.541427, 0.0001)
        Assert.assertEquals(location.hasFixedPrayerTime, true)
    }

    @Test
    fun geocoder_qasreIQ_isCorrect() = runBlocking {
        // Test (Qasre, IQ) which has fixed prayer times by  mapper.
        val location = MuslimRepository(context).geocoder("IQ", "Qasre")
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.latitude, 36.557804, 0.0001)
        Assert.assertEquals(location.longitude, 44.827805, 0.0001)
        Assert.assertEquals(location.hasFixedPrayerTime, true)
    }

    @Test
    fun reverseGeocoder_londonGB_isCorrect() = runBlocking {
        // Test (London, GB) which has fixed prayer times.
        val location = MuslimRepository(context).reverseGeocoder(51.50853, -0.12574)
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.name, "London")
        Assert.assertEquals(location.countryCode, "GB")
        Assert.assertEquals(location.countryName, "United Kingdom")
        Assert.assertEquals(location.hasFixedPrayerTime, true)
    }

    @Test
    fun reverseGeocoder_tehranIR_isCorrect() = runBlocking {
        // Test (Tehran, IR) which hasn't fixed prayer times.
        val location = MuslimRepository(context).reverseGeocoder(35.69439, 51.42151)
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.name, "Tehran")
        Assert.assertEquals(location.countryCode, "IR")
        Assert.assertEquals(location.countryName, "Iran")
        Assert.assertEquals(location.hasFixedPrayerTime, false)
    }

    @Test
    fun reverseGeocoder_soranIQ_isCorrect() = runBlocking {
        // Test (Soran, IQ) which has fixed prayer times by  mapper.
        val location = MuslimRepository(context).reverseGeocoder(36.652686, 44.541427)
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.name, "Soran")
        Assert.assertEquals(location.countryCode, "IQ")
        Assert.assertEquals(location.countryName, "Iraq")
        Assert.assertEquals(location.hasFixedPrayerTime, true)
    }

    @Test
    fun reverseGeocoder_qasreIQ_isCorrect() = runBlocking {
        // Test (Qasre, IQ) which has fixed prayer times by  mapper.
        val location = MuslimRepository(context).reverseGeocoder(36.557804, 44.827805)
        Assert.assertNotNull(location)
        Assert.assertEquals(location!!.name, "Qasre")
        Assert.assertEquals(location.countryCode, "IQ")
        Assert.assertEquals(location.countryName, "Iraq")
        Assert.assertEquals(location.hasFixedPrayerTime, true)
    }
}
