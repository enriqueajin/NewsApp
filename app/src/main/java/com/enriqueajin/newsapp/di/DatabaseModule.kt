package com.enriqueajin.newsapp.di

import android.content.Context
import androidx.room.Room
import com.enriqueajin.newsapp.data.ArticleDao
import com.enriqueajin.newsapp.data.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = ArticlesDatabase::class.java,
            name = "ArticlesDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(articleDatabase: ArticlesDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }
}