package dev.matsem.spacenews.shared.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "article")
internal data class ArticleEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "authors")
    val authors: List<String>,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "published_at")
    val publishedAt: Instant,

    @ColumnInfo("news_site")
    val newsSiteName: String
)
