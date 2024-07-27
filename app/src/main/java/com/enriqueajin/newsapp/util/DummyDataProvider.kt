package com.enriqueajin.newsapp.util

import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.data.network.model.NewsSource

object DummyDataProvider {

    fun getLatestNewsItems(): List<NewsItem> {
        return listOf(
            NewsItem(source = NewsSource("1", "NewsSource1"), author = "John Doe", title = "Real Madrid champion of UEFA Champions League", description = "Real Madrid wins their 14th UEFA Champions League title.", url = "http://example.com/real-madrid-champions", urlToImage = "http://example.com/images/real-madrid.jpg", publishedAt = "June 1st - 2024", content = "In an exciting final..."),
            NewsItem(source = NewsSource("2", "NewsSource2"), author = "Jane Smith", title = "Summer about to come in Poland", description = "The summer season is approaching in Poland.", url = "http://example.com/summer-poland", urlToImage = "http://example.com/images/summer.jpg", publishedAt = "May 31st - 2024", content = "As temperatures rise..."),
            NewsItem(source = NewsSource("3", "NewsSource3"), author = "Carlos Lopez", title = "Chaos in traffic in Guatemala", description = "Traffic chaos continues in Guatemala City.", url = "http://example.com/traffic-chaos", urlToImage = "http://example.com/images/traffic.jpg", publishedAt = "May 28th - 2024", content = "The traffic situation..."),
            NewsItem(source = NewsSource("4", "NewsSource4"), author = "Anna Kowalski", title = "Government offering loans to anyone", description = "New government program offers loans to citizens.", url = "http://example.com/government-loans", urlToImage = "http://example.com/images/loans.jpg", publishedAt = "June 3rd - 2024", content = "In a new initiative..."),
        )
    }

    fun getAllNewsItems(): List<NewsItem> {
        return listOf(
            NewsItem(source = NewsSource("5", "NewsSource5"), author = "Michael Brown", title = "Tech stocks soar to new heights", description = "Technology stocks hit new records.", url = "http://example.com/tech-stocks", urlToImage = "http://example.com/images/tech-stocks.jpg", publishedAt = "June 2nd - 2024", content = "The tech industry..."),
            NewsItem(source = NewsSource("6", "NewsSource6"), author = "Emily Davis", title = "New species discovered in the Amazon", description = "Scientists discover a new species in the Amazon rainforest.", url = "http://example.com/new-species", urlToImage = "http://example.com/images/new-species.jpg", publishedAt = "June 4th - 2024", content = "In a groundbreaking discovery..."),
            NewsItem(source = NewsSource("7", "NewsSource7"), author = "David Wilson", title = "Electric cars becoming more popular", description = "The popularity of electric cars is increasing.", url = "http://example.com/electric-cars", urlToImage = "http://example.com/images/electric-cars.jpg", publishedAt = "June 1st - 2024", content = "Electric cars are..."),
            NewsItem(source = NewsSource("8", "NewsSource8"), author = "Sophia Martinez", title = "Advances in medical technology", description = "New advancements in medical technology.", url = "http://example.com/medical-tech", urlToImage = "http://example.com/images/medical-tech.jpg", publishedAt = "May 30th - 2024", content = "Medical technology has..."),
            NewsItem(source = NewsSource("9", "NewsSource9"), author = "William Johnson", title = "Climate change impacts agriculture", description = "Climate change is affecting agricultural production.", url = "http://example.com/climate-change-agriculture", urlToImage = "http://example.com/images/climate-change.jpg", publishedAt = "May 29th - 2024", content = "Agricultural production..."),
            NewsItem(source = NewsSource("10", "NewsSource10"), author = "Olivia Anderson", title = "Breakthrough in renewable energy", description = "A major breakthrough in renewable energy technology.", url = "http://example.com/renewable-energy", urlToImage = "http://example.com/images/renewable-energy.jpg", publishedAt = "June 3rd - 2024", content = "In a significant development...")
        )
    }
}