package dev.kosrat.muslimdata.models

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
