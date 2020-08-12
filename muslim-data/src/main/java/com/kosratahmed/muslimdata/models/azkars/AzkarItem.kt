package com.kosratahmed.muslimdata.models.azkars

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Azkar Item class that holds azkar item information such as (item, translation, and reference).
 */
class AzkarItem private constructor(
    val item: String,
    val translation: String,
    val reference: String
) {
    companion object {
        internal fun mapDBAzkarItems(items: List<AzkarItemView>): List<AzkarItem> {
            return items.map { AzkarItem(it.item, it.translation, it.reference) }
        }
    }
}

/**
 * Azkar item class that will be used as azkar_item table.
 */
@Entity(tableName = "azkar_item")
internal data class AzkarItemDB(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "chapter_id") val chapterId: Long,
    val item: String
)

/**
 * Azkar item translation class that will be used as azkar_item_translation table.
 */
@Entity(tableName = "azkar_item_translation")
internal data class AzkarItemTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "item_id") val itemId: Long,
    val language: String,
    @ColumnInfo(name = "item_translation") val itemTranslation: String
)

/**
 * Azkar reference class that will be used as azkar_reference table.
 */
@Entity(tableName = "azkar_reference")
internal data class AzkarReference(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "item_id") val itemId: Long
)

/**
 * Azkar reference translation class that will be used as azkar_reference_translation table.
 */
@Entity(tableName = "azkar_reference_translation")
internal data class AzkarReferenceTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "reference_id") val referenceId: Long,
    val language: String,
    val reference: String
)

/**
 * Azkar item view class that will be used as azkar_item_view view.
 */
@DatabaseView(
    viewName = "azkar_item_view",
    value = "SELECT item._id, item.chapter_id AS chapterId, tr.language, item.item, tr.item_translation AS translation, rtr.reference " +
            "FROM azkar_item as item " +
            "INNER JOIN azkar_item_translation AS tr ON tr.item_id = item._id " +
            "INNER JOIN azkar_reference AS ref ON ref.item_id = item._id " +
            "INNER JOIN azkar_reference_translation AS rtr ON rtr.reference_id = ref._id AND rtr.language = tr.language"
)
internal data class AzkarItemView(
    val _id: Long,
    val chapterId: Long,
    val language: String,
    val item: String,
    val translation: String,
    val reference: String
)