package com.enriqueajin.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class ArticlesDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}