package com.kosratahmed.muslimdata.models.azkars

import androidx.room.*

class AzkarChapter private constructor(val chapterId: Long, val categoryId: Long, chapterName: String) {
    companion object {
        internal fun mapDBAzkarChapters(chapters: List<AzkarChapterWithTranslation>): List<AzkarChapter> {
            return chapters.map { AzkarChapter(it.azkarChapter._id, it.azkarChapter.categoryId, it.translation.chapterName) }
        }
    }
}

@Entity(tableName = "azkar_chapter")
internal data class AzkarChapterDB(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long
)

@Entity(tableName = "azkar_chapter_translation")
internal data class AzkarChapterTranslation(
    @PrimaryKey val _id: Long,
    @ColumnInfo(name = "chapter_id") val chapterId: Long,
    val language: String,
    @ColumnInfo(name = "chapter_name") val chapterName: String
)

internal data class AzkarChapterWithTranslation(
    @Embedded val translation: AzkarChapterTranslation,
    @Relation(parentColumn = "chapter_id", entityColumn = "_id")
    val azkarChapter: AzkarChapterDB
)
