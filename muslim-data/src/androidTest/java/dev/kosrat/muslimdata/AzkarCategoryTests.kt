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
class AzkarCategoryTests {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun teardown() {
    }

    @Test
    fun azkarCategories_enCategories_isCorrect() {
        testAzkarCategories(Language.EN)
    }

    @Test
    fun azkarCategories_arCategories_isCorrect() {
        testAzkarCategories(Language.AR)
    }

    @Test
    fun azkarCategories_ckbCategories_isCorrect() {
        testAzkarCategories(Language.CKB)
    }

    @Test
    fun azkarCategories_ckbBadiniCategories_isCorrect() {
        testAzkarCategories(Language.CKB_BADINI)
    }

    @Test
    fun azkarCategories_faCategories_isCorrect() {
        testAzkarCategories(Language.FA)
    }

    @Test
    fun azkarCategories_ruCategories_isCorrect() {
        testAzkarCategories(Language.RU)
    }

    private fun testAzkarCategories(language: Language) = runBlocking {
        val categories = MuslimRepository(context).getAzkarCategories(language)
        Assert.assertNotNull(categories)
        Assert.assertEquals(categories.size, 11)
        Assert.assertNotNull(categories[(0..10).random()].categoryName)
    }
}