package dev.kosrat.muslimdata

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.database.MuslimDataDao
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import dev.kosrat.muslimdata.models.Language
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AzkarCategoryTests {

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
    fun azkarCategories_faCategories_isCorrect() {
        testAzkarCategories(Language.FA)
    }

    @Test
    fun azkarCategories_ruCategories_isCorrect() {
        testAzkarCategories(Language.RU)
    }

    private fun testAzkarCategories(
        language: Language
    ) = muslimDataDao.getAzkarCategories(language = language.value).let { categories ->
        Assert.assertNotNull(categories)
        Assert.assertEquals(categories.size, 11)
        Assert.assertNotNull(categories[(0..10).random()].categoryName)
    }
}