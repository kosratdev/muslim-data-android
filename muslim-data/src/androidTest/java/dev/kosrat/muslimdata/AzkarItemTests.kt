package dev.kosrat.muslimdata

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.database.MuslimDataDao
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.models.Language.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AzkarItemTests {

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
    fun azkarItems_enItems_isCorrect() {
        testChapterItems(EN)
    }

    @Test
    fun azkarItems_arItems_isCorrect() {
        testChapterItems(AR)
    }

    @Test
    fun azkarItems_ckbItems_isCorrect() {
        testChapterItems(CKB)
    }

    @Test
    fun azkarItems_faItems_isCorrect() {
        testChapterItems(FA)
    }

    @Test
    fun azkarItems_ruItems_isCorrect() {
        testChapterItems(RU)
    }

    private fun testChapterItems(language: Language) {
        muslimDataDao.getAzkarItems(1, language.value).let { items ->
            assertNotNull(items)
            assertEquals(items!!.size, 4)
        }
    }
}
