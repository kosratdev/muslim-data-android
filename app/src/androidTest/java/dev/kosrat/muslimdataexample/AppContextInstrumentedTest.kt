package dev.kosrat.muslimdataexample

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppContextInstrumentedTest {
    @Test
    fun installedPackageMatchesBuildConfigApplicationId() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        assertEquals("dev.kosrat.muslimdataexample", appContext.packageName)
    }
}
