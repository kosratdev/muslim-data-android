package com.kosratahmed.muslimdata.models.prayertime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Prayer time class that will be used as prayer_time table.
 */
@Entity(
    tableName = "prayer_time",
    indices = [Index(name = "prayer_index", value = ["country_code", "city", "date"])]
)
internal data class FixedPrayerTime(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "country_code") val countryCode: String,
    val city: String,
    val date: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)