package com.kosratahmed.muslimdata.models

import androidx.room.*

@Entity(tableName = "name")
data class Name(
    @PrimaryKey val _id: Long,
    val name: String
)

@Entity(tableName = "name_translation")
data class NameTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "name_id") val nameId: Long,
    val language: String,
    val name: String
)

data class NameWithTranslation(
    @Embedded val translation: NameTranslation,
    @Relation(parentColumn = "name_id", entityColumn = "_id")
    val name: Name
)
