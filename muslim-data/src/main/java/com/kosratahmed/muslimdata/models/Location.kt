package com.kosratahmed.muslimdata.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Location class that holds all information about a location.
 */
data class Location(
    val countryCode: String,
    val countryName: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val hasFixedPrayerTime: Boolean
)

/**
 * City table class that will be used as city table.
 */
@Entity(
    tableName = "city",
    indices = [
        Index(name = "city_index", value = ["city_name"]),
        Index(name = "lat_long_index", value = ["latitude", "longitude"])
    ]
)
data class CityTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "country_code") val countryCode: String,
    @ColumnInfo(name = "city_name") val cityName: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "has_fixed_prayer_time") val hasFixedPrayerTime: Boolean
)

/**
 * Country table class that will be used as country table.
 */
@Entity(
    tableName = "country",
    indices = [Index(name = "country_code_index", value = ["country_code"])]
)
data class CountryTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "country_code") val countryCode: String,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo(name = "country_continent") val countryContinent: String,
    @ColumnInfo(name = "country_language") val countryLanguage: String
)