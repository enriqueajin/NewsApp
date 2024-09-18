package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.network.dto.toDomain
import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.util.FakeArticleProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddArticleToFavoritesUseCaseTest {

    @MockK
    private lateinit var newsRepositoryImpl: NewsRepositoryImpl

    private lateinit var addArticleToFavoritesUseCase: AddArticleToFavoritesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addArticleToFavoritesUseCase = AddArticleToFavoritesUseCase(newsRepositoryImpl)
    }

    @Test
    fun `should call repository function to add article to favorites when invoke`() = runBlocking {
        // Given
        val expectedArticle = FakeArticleProvider.getArticles().first().toDomain()
        coEvery { newsRepositoryImpl.addFavoriteArticle(any()) } returns Unit

        // When
        addArticleToFavoritesUseCase(expectedArticle)

        //Then
        coVerify(exactly = 1) { newsRepositoryImpl.addFavoriteArticle(any()) }
    }
}