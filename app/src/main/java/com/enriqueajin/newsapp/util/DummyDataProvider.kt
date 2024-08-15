package com.enriqueajin.newsapp.util

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.flowOf

object DummyDataProvider {

    fun getLatestNewsItems(): List<Article> {
        return listOf(
            Article(source = "BBC", author = "John Doe", title = "Real Madrid champion of UEFA Champions League", description = "Real Madrid wins their 14th UEFA Champions League title.", url = "http://example.com/real-madrid-champions", urlToImage = "http://example.com/images/real-madrid.jpg", publishedAt = "June 1st - 2024", content = "In an exciting final..."),
            Article(source = "New York Times", author = "Jane Smith", title = "Summer about to come in Poland", description = "The summer season is approaching in Poland.", url = "http://example.com/summer-poland", urlToImage = "http://example.com/images/summer.jpg", publishedAt = "May 31st - 2024", content = "As temperatures rise..."),
            Article(source = "Psychology Today", author = "Carlos Lopez", title = "Chaos in traffic in Guatemala", description = "Traffic chaos continues in Guatemala City.", url = "http://example.com/traffic-chaos", urlToImage = "http://example.com/images/traffic.jpg", publishedAt = "May 28th - 2024", content = "The traffic situation..."),
            Article(source = "The Verge", author = "Anna Kowalski", title = "Government offering loans to anyone", description = "New government program offers loans to citizens.", url = "http://example.com/government-loans", urlToImage = "http://example.com/images/loans.jpg", publishedAt = "June 3rd - 2024", content = "In a new initiative..."),
        )
    }

    fun getAllNewsItems(): List<Article> {
        return listOf(
            Article(source = "The Guardian", author = "Michael Brown", title = "Tech stocks soar to new heights", description = "Technology stocks hit new records.", url = "http://example.com/tech-stocks", urlToImage = "http://example.com/images/tech-stocks.jpg", publishedAt = "June 2nd - 2024", content = "The tech industry..."),
            Article(source = "The Verge", author = "Emily Davis", title = "New species discovered in the Amazon", description = "Scientists discover a new species in the Amazon rainforest.", url = "http://example.com/new-species", urlToImage = "http://example.com/images/new-species.jpg", publishedAt = "June 4th - 2024", content = "In a groundbreaking discovery..."),
            Article(source = "BBC", author = "David Wilson", title = "Electric cars becoming more popular", description = "The popularity of electric cars is increasing.", url = "http://example.com/electric-cars", urlToImage = "http://example.com/images/electric-cars.jpg", publishedAt = "June 1st - 2024", content = "Electric cars are..."),
            Article(source = "CNN", author = "Sophia Martinez", title = "Advances in medical technology", description = "New advancements in medical technology.", url = "http://example.com/medical-tech", urlToImage = "http://example.com/images/medical-tech.jpg", publishedAt = "May 30th - 2024", content = "Medical technology has..."),
            Article(source = "Psychology Today", author = "William Johnson", title = "Climate change impacts agriculture", description = "Climate change is affecting agricultural production.", url = "http://example.com/climate-change-agriculture", urlToImage = "http://example.com/images/climate-change.jpg", publishedAt = "May 29th - 2024", content = "Agricultural production..."),
            Article(source = "BBC", author = "Olivia Anderson", title = "Breakthrough in renewable energy", description = "A major breakthrough in renewable energy technology.", url = "http://example.com/renewable-energy", urlToImage = "http://example.com/images/renewable-energy.jpg", publishedAt = "June 3rd - 2024", content = "In a significant development...")
        )
    }

    @Composable
    fun <T: Any>  getFakeLazyPagingItems(data: List<T>): LazyPagingItems<T> {
        val pagingData = flowOf(PagingData.from(data))
        return pagingData.collectAsLazyPagingItems()
    }
}