package com.enriqueajin.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.enriqueajin.newsapp.domain.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM ArticleEntity")
    suspend fun getFavorites(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: ArticleEntity)

    @Update
    suspend fun updateArticle(article: ArticleEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("SELECT COUNT(*) FROM ArticleEntity where url = :id")
    suspend fun checkIsArticleFavorite(id: String): Int
}