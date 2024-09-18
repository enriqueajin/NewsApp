package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.network.dto.ArticleDto
import com.enriqueajin.newsapp.data.network.dto.toDomain
import com.enriqueajin.newsapp.data.network.response.NewsResponse
import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.util.FakeArticleProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNewsByCategoryUseCaseTest {

    @MockK
    private lateinit var newsRepository: NewsRepositoryImpl

    private lateinit var getNewsByCategoryUseCase: GetNewsByCategoryUseCase
    private lateinit var fakeArticles: List<ArticleDto>

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getNewsByCategoryUseCase = GetNewsByCategoryUseCase(newsRepository)
        fakeArticles = FakeArticleProvider.getArticles()
    }

    @Test
    fun `should return list of articles when repository returns articles`() = runBlocking {
        // Given
        coEvery { newsRepository.getArticlesByCategory(any()) } returns NewsResponse(
            "200",
            fakeArticles.size,
            fakeArticles
        )

        // When
        val response = getNewsByCategoryUseCase()

        // Then
        response.collect {
            val data = fakeArticles.map { article -> article.toDomain() }
            assert(data.isNotEmpty())
            assert(data == it)
        }
    }

    @Test
    fun `should return empty list when repository returns empty list of articles`() = runBlocking {
       // Given
        coEvery { newsRepository.getArticlesByCategory(any()) } returns NewsResponse(
            "200",
            0,
            emptyList()
        )

        // When
        val response = getNewsByCategoryUseCase()

        // Then
        response.collect {
            assert(it.isEmpty())
        }
    }

    @Test
    fun `should return articles when api returns articles except articles with title 'REMOVED'`() = runBlocking {
        // Given
        coEvery { newsRepository.getArticlesByCategory(any()) } returns NewsResponse(
            "200",
            fakeArticles.size,
            fakeArticles
        )

        // When
        val response = getNewsByCategoryUseCase()

        // Then
        response.collect {
            assert(fakeArticles.isNotEmpty())
            assert(fakeArticles.any { it.title != "[REMOVED]" })
        }
    }

    @Test
    fun `should call api with default category when none provided`() = runBlocking {
        // Given
        coEvery { newsRepository.getArticlesByCategory(any()) } returns NewsResponse(
            "200",
            fakeArticles.size,
            fakeArticles
        )

        // When
        val response = getNewsByCategoryUseCase()

        // Then
        response.collect {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun `should call api with the category provided`() = runBlocking {
        // Given
        coEvery { newsRepository.getArticlesByCategory(any()) } returns NewsResponse(
            "200",
            fakeArticles.size,
            fakeArticles
        )

        // When
        val response = getNewsByCategoryUseCase("Science")

        // Then
        response.collect {
            assert(it.isNotEmpty())
        }
    }
}


