package dev.kosrat.muslimdata

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.models.NameOfAllah
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
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
    fun namesOfAllah_enNames_isCorrect() = runBlocking {
        testNames(MuslimRepository(context).getNamesOfAllah(Language.EN))
    }

    @Test
    fun namesOfAllah_arNames_isCorrect() = runBlocking {
        testNames(MuslimRepository(context).getNamesOfAllah(Language.AR))
    }

    @Test
    fun namesOfAllah_ckbNames_isCorrect() = runBlocking {
        testNames(MuslimRepository(context).getNamesOfAllah(Language.CKB))
    }

    @Test
    fun namesOfAllah_faNames_isCorrect() = runBlocking {
        testNames(MuslimRepository(context).getNamesOfAllah(Language.FA))
    }

    @Test
    fun namesOfAllah_ruNames_isCorrect() = runBlocking {
        testNames(MuslimRepository(context).getNamesOfAllah(Language.RU))
    }

    private fun testNames(names: List<NameOfAllah>) {
        assertNotNull(names)
        assertNotEquals(names, emptyList<NameOfAllah>())
        assertEquals(names.size, 99)
    }
}
