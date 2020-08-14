package com.kosratahmed.muslimdata.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Name of Allah class that holds name of allah with translation.
 */
data class NameOfAllah internal constructor(val name: String, val translation: String)

/**
 * Name table class that will be used as name table.
 */
@Entity(tableName = "name")
internal data class NameTable(
    @PrimaryKey val _id: Long,
    val name: String
)

/**
 * Name translation table class that will be used as name_translation table.
 */
@Entity(tableName = "name_translation")
internal data class NameTranslationTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "name_id") val nameId: Long,
    val language: String,
    val name: String
)
