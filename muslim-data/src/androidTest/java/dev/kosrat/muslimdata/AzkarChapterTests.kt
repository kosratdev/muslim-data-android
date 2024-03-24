package dev.kosrat.muslimdata

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.repository.MuslimRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AzkarChapterTests {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun teardown() {
    }

    @Test
    fun azkarChapters_enChapters_isCorrect() {
        testAzkarChapters(Language.EN)
    }

    @Test
    fun azkarChapters_arChapters_isCorrect() {
        testAzkarChapters(Language.AR)
    }

    @Test
    fun azkarChapters_ckbChapters_isCorrect() {
        testAzkarChapters(Language.CKB)
    }

    @Test
    fun azkarChapters_faChapters_isCorrect() {
        testAzkarChapters(Language.FA)
    }

    @Test
    fun azkarChapters_ruChapters_isCorrect() {
        testAzkarChapters(Language.RU)
    }

    private fun testAzkarChapters(language: Language, categoryId: Int = -1, total: Int = 133) =
        runBlocking {
            val chapters = MuslimRepository(context).getAzkarChapters(language, categoryId)
            Assert.assertNotNull(chapters)
            Assert.assertEquals(chapters!!.size, total)
        }

    @Test
    fun azkarChapters_chapterIds_isCorrect() = runBlocking {
        val chapters = MuslimRepository(context).getAzkarChapters(
            Language.EN,
            (0..133).toList().toTypedArray()
        )
        Assert.assertNotNull(chapters)
        Assert.assertEquals(chapters!!.size, 133)
    }

    @Test
    fun azkarChaptersByCategory_enChapters_isCorrect() {
        testAzkarChapters(Language.EN, 1, 7)
        testAzkarChapters(Language.EN, 2, 14)
        testAzkarChapters(Language.EN, 3, 7)
        testAzkarChapters(Language.EN, 4, 15)
        testAzkarChapters(Language.EN, 5, 11)
        testAzkarChapters(Language.EN, 6, 19)
        testAzkarChapters(Language.EN, 7, 9)
        testAzkarChapters(Language.EN, 8, 8)
        testAzkarChapters(Language.EN, 9, 20)
        testAzkarChapters(Language.EN, 10, 10)
        testAzkarChapters(Language.EN, 11, 13)
    }

    @Test
    fun azkarChaptersByCategory_arChapters_isCorrect() {
        testAzkarChapters(Language.AR, 1, 7)
        testAzkarChapters(Language.AR, 2, 14)
        testAzkarChapters(Language.AR, 3, 7)
        testAzkarChapters(Language.AR, 4, 15)
        testAzkarChapters(Language.AR, 5, 11)
        testAzkarChapters(Language.AR, 6, 19)
        testAzkarChapters(Language.AR, 7, 9)
        testAzkarChapters(Language.AR, 8, 8)
        testAzkarChapters(Language.AR, 9, 20)
        testAzkarChapters(Language.AR, 10, 10)
        testAzkarChapters(Language.AR, 11, 13)
    }

    @Test
    fun azkarChaptersByCategory_ckbChapters_isCorrect() {
        testAzkarChapters(Language.CKB, 1, 7)
        testAzkarChapters(Language.CKB, 2, 14)
        testAzkarChapters(Language.CKB, 3, 7)
        testAzkarChapters(Language.CKB, 4, 15)
        testAzkarChapters(Language.CKB, 5, 11)
        testAzkarChapters(Language.CKB, 6, 19)
        testAzkarChapters(Language.CKB, 7, 9)
        testAzkarChapters(Language.CKB, 8, 8)
        testAzkarChapters(Language.CKB, 9, 20)
        testAzkarChapters(Language.CKB, 10, 10)
        testAzkarChapters(Language.CKB, 11, 13)
    }

    @Test
    fun azkarChaptersByCategory_faChapters_isCorrect() {
        testAzkarChapters(Language.FA, 1, 7)
        testAzkarChapters(Language.FA, 2, 14)
        testAzkarChapters(Language.FA, 3, 7)
        testAzkarChapters(Language.FA, 4, 15)
        testAzkarChapters(Language.FA, 5, 11)
        testAzkarChapters(Language.FA, 6, 19)
        testAzkarChapters(Language.FA, 7, 9)
        testAzkarChapters(Language.FA, 8, 8)
        testAzkarChapters(Language.FA, 9, 20)
        testAzkarChapters(Language.FA, 10, 10)
        testAzkarChapters(Language.FA, 11, 13)
    }

    @Test
    fun azkarChaptersByCategory_ruChapters_isCorrect() {
        testAzkarChapters(Language.RU, 1, 7)
        testAzkarChapters(Language.RU, 2, 14)
        testAzkarChapters(Language.RU, 3, 7)
        testAzkarChapters(Language.RU, 4, 15)
        testAzkarChapters(Language.RU, 5, 11)
        testAzkarChapters(Language.RU, 6, 19)
        testAzkarChapters(Language.RU, 7, 9)
        testAzkarChapters(Language.RU, 8, 8)
        testAzkarChapters(Language.RU, 9, 20)
        testAzkarChapters(Language.RU, 10, 10)
        testAzkarChapters(Language.RU, 11, 13)
    }
}