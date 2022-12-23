package com.enriqueajin.newsapp.ui.view.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enriqueajin.newsapp.databinding.NewsItemBinding
import com.enriqueajin.newsapp.domain.model.Article

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = NewsItemBinding.bind(view)

    fun render(
        article: Article,
        onClickListener: (Article) -> Unit,
        onClickBookmark: (Int) -> Unit
    ) {
        binding.tvNewsAuthor.text = article.author
        binding.tvNewsTitle.text = article.title
        binding.tvNewsDate.text = article.publishedAt
        Glide.with(binding.ivNews.context).load(article.urlToImage).into(binding.ivNews)
        binding.ibBookmark.setOnClickListener { onClickBookmark(adapterPosition) }
        binding.cardContainer.setOnClickListener { onClickListener(article) }
    }
}