package dev.matsem.spacenews.shared.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.matsem.spacenews.shared.data.database.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ArticleDao {

    @Upsert
    suspend fun upsert(article: ArticleEntity)

    @Query("SELECT * FROM article WHERE article.id = :id")
    fun getArticle(id: Int): Flow<ArticleEntity?>

    @Query("DELETE FROM article")
    suspend fun deleteAll()

    @Query("DELETE FROM article where article.id = :id")
    suspend fun deleteArticle(id: Int)
}
