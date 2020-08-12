package com.kosratahmed.muslimdata.models.azkars

import androidx.room.*

/**
 * Azkar Category class.
 */
class AzkarCategory private constructor(val categoryName: String) {
    companion object {
        internal fun mapDBAzkarCategories(categories: List<AzkarCategoryWithTranslation>): List<AzkarCategory> {
            return categories.map { AzkarCategory(it.translation.categoryName) }
        }
    }
}

/**
 * Azkar category class that will be used as azkar_category table.
 */
@Entity(tableName = "azkar_category")
internal data class AzkarCategoryDB(
    @PrimaryKey val _id: Long
)

/**
 * Azkar category translation class that will be used as azkar_category_translation table.
 */
@Entity(tableName = "azkar_category_translation")
internal data class AzkarCategoryTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val language: String,
    @ColumnInfo(name = "category_name") val categoryName: String
)

/**
 * Azkar category with translation class that will be used as a relationship between azkar_category
 * and azkar_category_translation table.
 */
internal data class AzkarCategoryWithTranslation(
    @Embedded val translation: AzkarCategoryTranslation,
    @Relation(parentColumn = "category_id", entityColumn = "_id")
    val azkarCategory: AzkarCategoryDB
)
