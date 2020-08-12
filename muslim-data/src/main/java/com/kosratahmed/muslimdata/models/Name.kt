package com.kosratahmed.muslimdata.models

import androidx.room.*

/**
 * Name of Allah class that holds name of allah with translation.
 */
class NameOfAllah private constructor(val name: String, val translation: String) {
    companion object {
        internal fun mapDBNames(dbNames: List<NameWithTranslation>): List<NameOfAllah> {
            return dbNames.map { NameOfAllah(it.name.name, it.translation.name) }
        }
    }
}

/**
 * Name class that will be used as name table.
 */
@Entity(tableName = "name")
internal data class Name(
    @PrimaryKey val _id: Long,
    val name: String
)

/**
 * Name translation class that will be used as name_translation table.
 */
@Entity(tableName = "name_translation")
internal data class NameTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "name_id") val nameId: Long,
    val language: String,
    val name: String
)

/**
 * Name with translation class that will be used as a relationship between name and name_translation
 * table.
 */
internal data class NameWithTranslation(
    @Embedded val translation: NameTranslation,
    @Relation(parentColumn = "name_id", entityColumn = "_id")
    val name: Name
)
