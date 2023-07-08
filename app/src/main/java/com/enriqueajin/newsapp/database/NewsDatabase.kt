package com.enriqueajin.newsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enriqueajin.newsapp.database.dao.ArticleDAO
import com.enriqueajin.newsapp.database.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun getGeneralNewsDao(): ArticleDAO
}