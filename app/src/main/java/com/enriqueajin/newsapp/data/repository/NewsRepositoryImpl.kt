package com.enriqueajin.newsapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteFullException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.local.ArticleDao
import com.enriqueajin.newsapp.data.local.ArticleEntity
import com.enriqueajin.newsapp.data.network.NewsApiClient
import com.enriqueajin.newsapp.data.network.NewsPagingSource
import com.enriqueajin.newsapp.data.network.dto.toDomain
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.repository.NewsRepository
import com.enriqueajin.newsapp.util.Constants.ALL_NEWS_PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.PREFETCH_ITEMS
import com.enriqueajin.newsapp.util.Constants.REMOVED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiClient,
    private val dao: ArticleDao
) : NewsRepository {

    /**
     * Get articles by category from Api for HomeScreen (without pagination)
     * @param category category for which articles will be sorted
     * @return Flow with the list of articles
     */
    override fun getArticlesByCategory(category: String): Flow<Result<List<Article>, DataError.Network>> = flow {
        val response = callApi {
            api.getArticlesByCategory(
                category = category,
                page = 1,
                pageSize = ALL_NEWS_PAGE_SIZE
            ).articles
                .map { article -> article.toDomain() }
                .filter { it.title != REMOVED }
        }
        emit(response)
    }

    /**
     * Get articles by category from Api with pagination
     * @param category category for which articles will be sorted
     * @return Flow with the list of articles
     */
    override fun getPagingArticlesByCategory(category: String): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
                    category = category,
                )
            }).flow
    }

    /**
     * Get articles by keyword from Api for HomeScreen (without pagination)
     * @param keyword Keyword for which articles will be searched
     * @return Flow with the list of articles
     */
    override suspend fun getArticlesByKeyword(keyword: String): Flow<Result<List<Article>, DataError.Network>>  = flow{
        val response = callApi {
            api.getArticlesByKeyword(
                keyword = keyword,
                page = 1,
                pageSize = ALL_NEWS_PAGE_SIZE
            ).articles
                .map { article -> article.toDomain() }
                .filter { it.title != REMOVED }
        }
        emit(response)
    }

    /**
     * Get articles by keyword from Api with pagination
     * @param keyword Keyword for which articles will be searched
     * @return Flow with the list of articles
     */
    override fun getPagingArticlesByKeyword(keyword: String): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
                    keyword = keyword,
                )
            }).flow
    }

    override fun getFavorites(): Flow<Result<List<Article>, DataError.Database>> = flow {
        val response = runQuery {
            dao.getFavorites()
        }
        emit(response)
    }

    override suspend fun addFavoriteArticle(article: ArticleEntity): Flow<Result<Unit, DataError.Database>> = flow {
        val response = runQuery {
            dao.addArticle(article)
        }
        emit(response)
    }

    override suspend fun deleteFavoriteArticle(article: ArticleEntity): Flow<Result<Unit, DataError.Database>> = flow{
        val response = runQuery {
            dao.deleteArticle(article)
        }
        emit(response)
    }

    override fun checkIsArticleFavorite(articleId: String): Flow<Result<Boolean, DataError.Database>> = flow {
        val response = runQuery {
            dao.checkIsArticleFavorite(articleId) > 0
        }
        emit(response)
    }

    private suspend fun <T> callApi(apiCall: suspend () -> T): Result<T, DataError.Network> {
        return try {
            Result.Success(apiCall())
        } catch (e: UnresolvedAddressException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: UnknownHostException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.Error(DataError.Network.UNAUTHORIZED)
                409 -> Result.Error(DataError.Network.CONFLICT)
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                426 -> Result.Error(DataError.Network.UPGRADE_REQUIRED)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    private suspend fun <T> runQuery(query: suspend () -> T): Result<T, DataError.Database> {
        return try {
            Result.Success(query())
        } catch (e: SQLiteConstraintException) {
            Result.Error(DataError.Database.CONSTRAINT_VIOLATION)
        } catch (e: SQLiteDatabaseCorruptException) {
            Result.Error(DataError.Database.DATABASE_CORRUPTED)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Database.STORAGE_FULL)
        } catch (e: Exception) {
            Result.Error(DataError.Database.UNKNOWN)
        }
    }
}