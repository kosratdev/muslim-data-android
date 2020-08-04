package com.kosratahmed.muslimdata.models.azkars

import androidx.room.*

@Entity(tableName = "azkar_category")
data class AzkarCategory(
    @PrimaryKey val _id: Long
)

@Entity(tableName = "azkar_category_translation")
data class AzkarCategoryTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val language: String,
    @ColumnInfo(name = "category_name") val categoryName: String
)

data class AzkarCategoryWithTranslation(
    @Embedded val translation: AzkarCategoryTranslation,
    @Relation(parentColumn = "category_id", entityColumn = "_id")
    val azkarCategory: AzkarCategory
)
