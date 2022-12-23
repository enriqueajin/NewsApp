package com.enriqueajin.newsapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
)
