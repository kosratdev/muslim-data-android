package dev.kosrat.muslimdata.models

/**
 * Azkar Item class that holds azkar item information such as (item, translation, and reference).
 */
data class AzkarItem internal constructor(
    val itemId: Long,
    val chapterId: Long,
    val language: String,
    val item: String,
    val translation: String,
    val reference: String
)
