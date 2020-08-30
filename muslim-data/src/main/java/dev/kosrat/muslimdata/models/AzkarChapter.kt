package dev.kosrat.muslimdata.models

/**
 * Azkar Chapter class that holds azkar chapter information such as (chapterId, categoryId, and
 * chapterName).
 */
data class AzkarChapter internal constructor(
    val chapterId: Int,
    val categoryId: Int,
    val chapterName: String
)
