package com.enriqueajin.newsapp.util

import com.enriqueajin.newsapp.data.network.model.NewsItem

object DummyDataProvider {

    fun getLatestNewsItems(): List<NewsItem> {
        return listOf(
            NewsItem(title = "Real Madrid champion of UEFA Champions League", publishedAt = "June 1st - 2024"),
            NewsItem(title = "Summer about to come in Poland", publishedAt = "May 31st - 2024"),
            NewsItem(title = "Chaos in traffic in Guatemala", publishedAt = "May 28th - 2024"),
            NewsItem(title = "Government offering loans to anyone", publishedAt = "June 3rd - 2024")
        )
    }

    fun getAllNewsItems(): List<NewsItem> {
        return listOf(
            NewsItem(title = "Israel implements new strategy", publishedAt = "May 28th - 2024", urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/dc7e/live/8f7f6800-1e60-11ef-80aa-699d54c46324.jpg", author = "BBC News"),
            NewsItem(title = "Action initiated to suspend activities", publishedAt = "May 31st - 2024", urlToImage = "https://www.hindustantimes.com/ht-img/img/2024/05/30/1600x900/Prajwal-Revanna-_passport_1717067247007_1717067247177.jpg"),
            NewsItem(title = "Nurse fired for calling Gaza war", publishedAt = "May 28th - 2024", urlToImage = "https://assets2.cbsnewsstatic.com/hub/i/r/2024/05/30/0f8f01c2-5852-4c4b-a83f-f499650c280e/thumbnail/1200x630/2ee144f1369a8477e0aa654b95913568/ap24150729635312.jpg?v=c5044be0004eac09882c007ac02fef6d"),
            NewsItem(title = "Home remedies to protect eyes during heatwave", publishedAt = "June 3rd - 2024", urlToImage = "https://static.toiimg.com/thumb/msid-110563215,width-1070,height-580,imgsize-33460,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg"),
            NewsItem(title = "Israel implements new strategy", publishedAt = "May 28th - 2024", urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/dc7e/live/8f7f6800-1e60-11ef-80aa-699d54c46324.jpg", author = "BBC News"),
            NewsItem(title = "Action initiated to suspend activities", publishedAt = "May 31st - 2024", urlToImage = "https://www.hindustantimes.com/ht-img/img/2024/05/30/1600x900/Prajwal-Revanna-_passport_1717067247007_1717067247177.jpg"),
            NewsItem(title = "Nurse fired for calling Gaza war", publishedAt = "May 28th - 2024", urlToImage = "https://assets2.cbsnewsstatic.com/hub/i/r/2024/05/30/0f8f01c2-5852-4c4b-a83f-f499650c280e/thumbnail/1200x630/2ee144f1369a8477e0aa654b95913568/ap24150729635312.jpg?v=c5044be0004eac09882c007ac02fef6d"),
            NewsItem(title = "Home remedies to protect eyes during heatwave", publishedAt = "June 3rd - 2024", urlToImage = "https://static.toiimg.com/thumb/msid-110563215,width-1070,height-580,imgsize-33460,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg")
        )
    }
}