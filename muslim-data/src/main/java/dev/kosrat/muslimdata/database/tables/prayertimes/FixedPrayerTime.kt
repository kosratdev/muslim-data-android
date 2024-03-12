package dev.kosrat.muslimdata.database.tables.prayertimes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.kosrat.muslimdata.database.tables.LocationTable

/**
 * Prayer time class that will be used as prayer_time table.
 */
@Entity(
    tableName = "prayer_time",
    foreignKeys = [
        ForeignKey(
            entity = LocationTable::class,
            parentColumns = ["_id"],
            childColumns = ["location_id"]
        ),
    ],
    indices = [Index(name = "prayer_index", value = ["location_id", "date"])]
)
internal data class FixedPrayerTime(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "location_id") val locationId: Int,
    val date: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)