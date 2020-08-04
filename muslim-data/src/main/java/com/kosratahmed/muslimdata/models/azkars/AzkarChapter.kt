package com.kosratahmed.muslimdata.models.azkars

import androidx.room.*

@Entity(tableName = "azkar_chapter")
data class AzkarChapter(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long
)

@Entity(tableName = "azkar_chapter_translation")
data class AzkarChapterTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "chapter_id") val chapterId: Long,
    val language: String,
    @ColumnInfo(name = "chapter_name") val chapterName: String
)

data class AzkarChapterWithTranslation(
    @Embedded val translation: AzkarChapterTranslation,
    @Relation(parentColumn = "chapter_id", entityColumn = "_id")
    val azkarChapter: AzkarChapter
)
