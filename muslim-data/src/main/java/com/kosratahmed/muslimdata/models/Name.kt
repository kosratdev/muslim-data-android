package com.kosratahmed.muslimdata.models

import androidx.room.*

class NameOfAllah private constructor(val name: String, val translation: String) {
    companion object {
        internal fun mapDBNames(dbNames: List<NameWithTranslation>): List<NameOfAllah> {
            return dbNames.map { NameOfAllah(it.name.name, it.translation.name) }
        }
    }
}

@Entity(tableName = "name")
internal data class Name(
    @PrimaryKey val _id: Long,
    val name: String
)

@Entity(tableName = "name_translation")
internal data class NameTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "name_id") val nameId: Long,
    val language: String,
    val name: String
)

internal data class NameWithTranslation(
    @Embedded val translation: NameTranslation,
    @Relation(parentColumn = "name_id", entityColumn = "_id")
    val name: Name
)
