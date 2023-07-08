package com.enriqueajin.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enriqueajin.newsapp.database.entities.ArticleEntity

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM article_table WHERE category = :category ORDER BY author DESC")
    suspend fun getNewsByCategory(category: String): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(news: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleToBookmarks(article: ArticleEntity)

    @Query("DELETE FROM article_table")
    suspend fun deleteAllNews()
}