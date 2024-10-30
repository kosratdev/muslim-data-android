package dev.kosrat.muslimdata

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.models.NameOfAllah
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NamesTests {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun teardown() {
    }

    @Test
    fun namesOfAllah_enNames_isCorrect() {
        testNames(Language.EN)
    }

    @Test
    fun namesOfAllah_arNames_isCorrect() {
        testNames(Language.AR)
    }

    @Test
    fun namesOfAllah_ckbNames_isCorrect() {
        testNames(Language.CKB)
    }

    @Test
    fun namesOfAllah_ckbBadiniNames_isCorrect() {
        testNames(Language.CKB_BADINI)
    }

    @Test
    fun namesOfAllah_faNames_isCorrect() {
        testNames(Language.FA)
    }

    @Test
    fun namesOfAllah_ruNames_isCorrect() {
        testNames(Language.RU)
    }

    private fun testNames(language: Language) = runBlocking {
        val names = MuslimRepository(context).getNamesOfAllah(language)
        assertNotNull(names)
        assertNotEquals(names, emptyList<NameOfAllah>())
        assertEquals(names.size, 99)
    }
}
