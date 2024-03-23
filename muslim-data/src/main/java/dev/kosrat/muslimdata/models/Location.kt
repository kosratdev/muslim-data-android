package dev.kosrat.muslimdata.models

/**
 * Location class that holds all information about a location.
 */
data class Location(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String,
    val countryName: String,
    val hasFixedPrayerTime: Boolean,
    val prayerDependentId: Int?
)
