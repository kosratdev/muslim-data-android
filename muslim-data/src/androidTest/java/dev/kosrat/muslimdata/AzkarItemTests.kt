package dev.kosrat.muslimdata

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AzkarItemTests {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun teardown() {
    }

    @Test
    fun azkarItems_enItems_isCorrect() {
        testChapterItems(Language.EN, 1, 4)
    }

    @Test
    fun azkarItems_arItems_isCorrect() {
        testChapterItems(Language.AR, 25, 8)
    }

    @Test
    fun azkarItems_ckbItems_isCorrect() {
        testChapterItems(Language.CKB, 50, 2)
    }

    @Test
    fun azkarItems_ckbBadiniItems_isCorrect() {
        testChapterItems(Language.CKB_BADINI, 50, 2)
    }

    @Test
    fun azkarItems_faItems_isCorrect() {
        testChapterItems(Language.FA, 75, 1)
    }

    @Test
    fun azkarItems_ruItems_isCorrect() {
        testChapterItems(Language.RU, 100, 1)
    }

    private fun testChapterItems(language: Language, id: Int, total: Int) = runBlocking {
        val items = MuslimRepository(context).getAzkarItems(id, language)
        assertNotNull(items)
        assertEquals(items!!.size, total)
    }
}
