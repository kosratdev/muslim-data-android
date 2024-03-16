package dev.kosrat.muslimdata

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.database.MuslimDataDao
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import dev.kosrat.muslimdata.models.Language
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AzkarChapterTests {

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

    private fun testAzkarChapters(language: Language) {
        muslimDataDao.getAzkarChapters(
            language = language.value,
            azkarIds = (0..133).toList().toTypedArray()
        ).let { chapters ->
            Assert.assertNotNull(chapters)
            Assert.assertEquals(chapters!!.size, 133)
        }
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

    private fun testAzkarChaptersByCategory(language: Language) {
        // Test English azkar chapters for the category
        muslimDataDao.getAzkarChapters(language.value, 1).let { chapters ->
            Assert.assertNotNull(chapters)
            Assert.assertEquals(chapters!!.size, 7)
        }
    }
}