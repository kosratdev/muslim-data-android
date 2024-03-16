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
class AzkarTests {

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
    fun testAzkarCategories() {
        testLanguageCategories(language = EN, nameCheckIndex = 5)
        testLanguageCategories(language = AR, nameCheckIndex = 10)
        testLanguageCategories(language = CKB, nameCheckIndex = 3)
        testLanguageCategories(language = FA, nameCheckIndex = 1)
        testLanguageCategories(language = RU, nameCheckIndex = 7)
    }

    private fun testLanguageCategories(
        language: Language,
        expectedCount: Int? = 11,
        nameCheckIndex: Int
    ) = muslimDataDao.getAzkarCategories(language = language.value).let { categories ->
        assertNotNull(categories)
        assertEquals(categories.size, expectedCount)
        assertNotNull(categories[nameCheckIndex].categoryName)
    }

    @Test
    fun testAzkarChapters() {
        testLanguageChapters(EN)
        testLanguageChapters(AR)
        testLanguageChapters(CKB)
        testLanguageChapters(FA)
        testLanguageChapters(RU)
    }

    private fun testLanguageChapters(language: Language, expectedCount: Int? = 133) {
        muslimDataDao.getAzkarChapters(
            language = language.value,
            azkarIds = (0..133).toList().toTypedArray()
        ).let { chapters ->
            assertNotNull(chapters)
            assertEquals(chapters!!.size, expectedCount)
        }
    }

    @Test
    fun testAzkarChaptersByCategory() {
        // Test English azkar chapters for different categories
        testChaptersByCategory(EN, 1, 7)
        testChaptersByCategory(EN, 2, 14)
        // ... add more tests for other categories
    }

    private fun testChaptersByCategory(language: Language, categoryId: Int, expectedCount: Int) {
        muslimDataDao.getAzkarChapters(
            language = language.value,
            categoryId = categoryId
        ).let { chapters ->
            assertNotNull(chapters)
            assertEquals(chapters!!.size, expectedCount)
        }
    }

    @Test
    fun testAzkarItems() {
        testItemsByChapter(EN, 1, 4)
        testItemsByChapter(EN, 10, 2)
        testItemsByChapter(EN, 100, 1)
    }

    private fun testItemsByChapter(language: Language, chapterId: Int, expectedCount: Int) {
        muslimDataDao.getAzkarItems(language = language.value, chapterId = chapterId).let { items ->
            assertNotNull(items)
            assertEquals(items!!.size, expectedCount)
        }
    }
}
