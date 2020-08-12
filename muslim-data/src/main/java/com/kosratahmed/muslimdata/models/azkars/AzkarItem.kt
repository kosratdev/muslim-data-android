package com.kosratahmed.muslimdata.models.azkars

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "azkar_item")
data class AzkarItem(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "chapter_id") val chapterId: Long,
    val item: String
)

@Entity(tableName = "azkar_item_translation")
data class AzkarItemTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "item_id") val itemId: Long,
    val language: String,
    @ColumnInfo(name = "item_translation") val itemTranslation: String
)

@Entity(tableName = "azkar_reference")
data class AzkarReference(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "item_id") val itemId: Long
)

@Entity(tableName = "azkar_reference_translation")
data class AzkarReferenceTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "reference_id") val referenceId: Long,
    val language: String,
    val reference: String
)

@DatabaseView(viewName = "azkar_view",
     value = "SELECT item._id, item.chapter_id AS chapterId, tr.language, item.item, tr.item_translation AS translation, rtr.reference " +
            "FROM azkar_item as item " +
            "INNER JOIN azkar_item_translation AS tr ON tr.item_id = item._id " +
            "INNER JOIN azkar_reference AS ref ON ref.item_id = item._id " +
            "INNER JOIN azkar_reference_translation AS rtr ON rtr.reference_id = ref._id AND rtr.language = tr.language"
)
data class AzkarItemDetail(
    val _id: Long,
    val chapterId: Long,
    val language: String,
    val item: String,
    val translation: String,
    val reference: String
)