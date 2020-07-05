package com.kosratahmed.muslimdata.repository

import android.content.Context
import com.kosratahmed.muslimdata.database.MuslimDataDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository (context: Context) {
    private val muslimDb = MuslimDataDatabase.getInstance(context)

    suspend fun searchCity(city: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.searchCity("$city%")
    }

    suspend fun geoCoder(countryCode: String, city: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.geoCoder(countryCode, city)
    }

    suspend fun geoCoder(latitude: Double, longitude: Double) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.geoCoder(latitude, longitude)
    }

    suspend fun getPrayerTimes(city: String) = withContext(Dispatchers.IO) {
        muslimDb.muslimDataDao.getPrayerTimes(city)
    }
}