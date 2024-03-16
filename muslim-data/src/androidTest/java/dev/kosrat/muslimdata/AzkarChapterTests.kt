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

    private fun testAzkarChapters(language: Language) = runBlocking {
        val chapters = MuslimRepository(context).getAzkarChapters(
            language,
            (0..133).toList().toTypedArray()
        )
        Assert.assertNotNull(chapters)
        Assert.assertEquals(chapters!!.size, 133)
    }

    @Test
    fun azkarChaptersByCategory_enChapters_isCorrect() {
        testAzkarChaptersByCategory(Language.EN)
    }

    @Test
    fun azkarChaptersByCategory_arChapters_isCorrect() {
        testAzkarChaptersByCategory(Language.AR)
    }

    @Test
    fun azkarChaptersByCategory_ckbChapters_isCorrect() {
        testAzkarChaptersByCategory(Language.CKB)
    }

    @Test
    fun azkarChaptersByCategory_faChapters_isCorrect() {
        testAzkarChaptersByCategory(Language.FA)
    }

    @Test
    fun azkarChaptersByCategory_ruChapters_isCorrect() {
        testAzkarChaptersByCategory(Language.RU)
    }

    private fun testAzkarChaptersByCategory(language: Language) = runBlocking {
        // Test English azkar chapters for the category
        val chapters = MuslimRepository(context).getAzkarChapters(language, 1)
        Assert.assertNotNull(chapters)
        Assert.assertEquals(chapters!!.size, 7)
    }
}