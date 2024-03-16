package dev.kosrat.muslimdata

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.kosrat.muslimdata.database.MuslimDataDao
import dev.kosrat.muslimdata.database.MuslimDataDatabase
import dev.kosrat.muslimdata.models.Language
import dev.kosrat.muslimdata.models.NameOfAllah
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NamesTests {

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
    fun namesOfAllah_englishNames_isCorrect() {
        testNames(muslimDataDao.getNames(Language.EN.value))
    }

    @Test
    fun namesOfAllah_arabicNames_isCorrect() {
        testNames(muslimDataDao.getNames(Language.AR.value))
    }

    @Test
    fun namesOfAllah_kurdishNames_isCorrect() {
        testNames(muslimDataDao.getNames(Language.CKB.value))
    }

    @Test
    fun namesOfAllah_persianNames_isCorrect() {
        testNames(muslimDataDao.getNames(Language.FA.value))
    }

    @Test
    fun namesOfAllah_russianNames_isCorrect() {
        testNames(muslimDataDao.getNames(Language.RU.value))
    }

    private fun testNames(names: List<NameOfAllah>) {
        assertNotNull(names)
        assertNotEquals(names, emptyList<NameOfAllah>())
        assertEquals(names.size, 99)
    }
}
