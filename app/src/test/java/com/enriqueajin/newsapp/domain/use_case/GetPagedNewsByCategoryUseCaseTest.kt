package com.enriqueajin.newsapp.domain.use_case

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.network.dto.ArticleDto
import com.enriqueajin.newsapp.data.network.dto.toDomain
import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.FakeArticleProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPagedNewsByCategoryUseCaseTest {

    @MockK
    private lateinit var newsRepositoryImpl: NewsRepositoryImpl

    private lateinit var getPagedNewsByCategoryUseCase: GetPagedNewsByCategoryUseCase
    private lateinit var fakeArticles: List<ArticleDto>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getPagedNewsByCategoryUseCase = GetPagedNewsByCategoryUseCase(newsRepositoryImpl)
        fakeArticles = FakeArticleProvider.getArticles()
    }

    @Test
    fun `should return paging data with articles`() = runBlocking {
        // Given
        val expectedResult = PagingData.from(fakeArticles.map { it.toDomain() })
//        val expectedResult = flowOf(PagingSource.LoadResult.Page(
//            data = listOf(getArticles()),
//            prevKey = null,
//            nextKey = null
//        ))
        every { newsRepositoryImpl.getPagingArticlesByCategory(any()) } returns flowOf(expectedResult)

        // When
        val result = getPagedNewsByCategoryUseCase()

        // Then
        result.collectLatest {
            assert(it == expectedResult)
        }

    }

    @Test
    fun `should return empty paging data when api returns empty list of articles`() = runBlocking {

        val expectedResult = PagingData.empty<Article>()

        every { newsRepositoryImpl.getPagingArticlesByCategory(any()) } returns flowOf(expectedResult)

        // When
        val result = getPagedNewsByCategoryUseCase()

        // Then
        result.collect {
            assert(it == expectedResult)
        }
    }
}