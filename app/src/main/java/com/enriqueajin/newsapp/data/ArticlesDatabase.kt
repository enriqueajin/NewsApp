package com.enriqueajin.newsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class ArticlesDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}