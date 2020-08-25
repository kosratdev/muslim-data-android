package dev.kosrat.muslimdata.database.tables.azkars

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Azkar item table class that will be used as azkar_item table.
 */
@Entity(tableName = "azkar_item")
internal data class AzkarItemTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "chapter_id") val chapterId: Long,
    val item: String
)

/**
 * Azkar item translation table class that will be used as azkar_item_translation table.
 */
@Entity(tableName = "azkar_item_translation")
internal data class AzkarItemTranslationTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "item_id") val itemId: Long,
    val language: String,
    @ColumnInfo(name = "item_translation") val itemTranslation: String
)

/**
 * Azkar reference table class that will be used as azkar_reference table.
 */
@Entity(tableName = "azkar_reference")
internal data class AzkarReferenceTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "item_id") val itemId: Long
)

/**
 * Azkar reference translation table class that will be used as azkar_reference_translation table.
 */
@Entity(tableName = "azkar_reference_translation")
internal data class AzkarReferenceTranslationTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "reference_id") val referenceId: Long,
    val language: String,
    val reference: String
)