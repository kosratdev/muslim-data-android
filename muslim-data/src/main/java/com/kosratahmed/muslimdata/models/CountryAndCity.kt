package com.kosratahmed.muslimdata.models

import androidx.room.*

/**
 * User location class that holds all information about user's location.
 */
class UserLocation private constructor(
    val countryCode: String,
    val countryName: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val hasFixedPrayerTime: Boolean
) {
    companion object {
        /**
         * Map CountryAndCity object to UserLocation object.
         */
        internal fun mapDBLocation(location: CountryAndCity): UserLocation {
            return UserLocation(
                location.country.countryCode,
                location.country.countryName,
                location.city.cityName,
                location.city.latitude,
                location.city.longitude,
                location.city.hasFixedPrayerTime
            )
        }

        /**
         * Map list of CountryAndCity object to list of UserLocation object.
         */
        internal fun mapDBLocations(locations: List<CountryAndCity>): List<UserLocation> {
            return locations.map {
                UserLocation(
                    it.country.countryCode,
                    it.country.countryName,
                    it.city.cityName,
                    it.city.latitude,
                    it.city.longitude,
                    it.city.hasFixedPrayerTime
                )
            }
        }
    }
}

/**
 * City class that will be used as city table.
 */
@Entity(
    tableName = "city",
    indices = [
        Index(name = "city_index", value = ["city_name"]),
        Index(name = "lat_long_index", value = ["latitude", "longitude"])
    ]
)
data class City(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "country_code") val countryCode: String,
    @ColumnInfo(name = "city_name") val cityName: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "has_fixed_prayer_time") val hasFixedPrayerTime: Boolean
)

/**
 * Country class that will be used as country table.
 */
@Entity(
    tableName = "country",
    indices = [Index(name = "country_code_index", value = ["country_code"])]
)
data class Country(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "country_code") val countryCode: String,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo(name = "country_continent") val countryContinent: String,
    @ColumnInfo(name = "country_language") val countryLanguage: String
)

/**
 * Country and city class that will be used as a relationship between city and country table.
 */
data class CountryAndCity(
    @Embedded val city: City,
    @Relation(parentColumn = "country_code", entityColumn = "country_code")
    val country: Country
)
