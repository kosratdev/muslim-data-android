package dev.kosrat.muslimdata.models.azkars

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Azkar Category class.
 */
data class AzkarCategory internal constructor(val categoryId: Long, val categoryName: String)

/**
 * Azkar category table class that will be used as azkar_category table.
 */
@Entity(tableName = "azkar_category")
internal data class AzkarCategoryTable(
    @PrimaryKey val _id: Long
)

/**
 * Azkar category translation table class that will be used as azkar_category_translation table.
 */
@Entity(tableName = "azkar_category_translation")
internal data class AzkarCategoryTranslationTable(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val language: String,
    @ColumnInfo(name = "category_name") val categoryName: String
)
