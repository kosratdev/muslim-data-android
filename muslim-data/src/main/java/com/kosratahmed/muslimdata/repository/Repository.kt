package com.kosratahmed.muslimdata.repository

import android.content.Context
import com.kosratahmed.muslimdata.database.MuslimDataDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository (context: Context) {
    private val muslimDb = MuslimDataDatabase.getInstance(context)

    suspend fun getPrayerTimes(city: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.getPrayerTimes(city)
    }
}