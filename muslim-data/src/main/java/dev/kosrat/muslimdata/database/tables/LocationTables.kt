package dev.kosrat.muslimdata.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * City table class that will be used as city table.
 */
@Entity(
    tableName = "location",
    foreignKeys = [
        ForeignKey(
            entity = CountryTable::class,
            parentColumns = ["_id"],
            childColumns = ["country_id"]
        ),
        ForeignKey(
            entity = LocationTable::class,
            parentColumns = ["_id"],
            childColumns = ["prayer_dependent_id"]
        ),
    ],
    indices = [
        Index(
            name = "location_name_index",
            value = ["name"]
        ),
        Index(
            name = "location_lat_long_index",
            value = ["latitude", "longitude"]
        )
    ]
)
internal data class LocationTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "country_id") val countryId: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "has_fixed_prayer_time", defaultValue = "0") val hasFixedPrayerTime: Boolean,
    @ColumnInfo(name = "prayer_dependent_id") val prayerDependentId: Int?
)

/**
 * Country table class that will be used as country table.
 */
@Entity(
    tableName = "country",
    indices = [Index(name = "country_index", value = ["code"])]
)
internal data class CountryTable(
    @PrimaryKey val _id: Long,
    val code: String,
    val name: String,
    val continent: String,
    val language: String
)