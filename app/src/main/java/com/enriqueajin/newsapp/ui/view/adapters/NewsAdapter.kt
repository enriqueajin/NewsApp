package com.enriqueajin.newsapp.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.domain.model.Article

class NewsAdapter(
    private var newsList: List<Article>,
    private var context: Context,
    private var onClickListener: (Article) -> Unit,
    private var onClickBookmark: (Int) -> Unit

): RecyclerView.Adapter<NewsViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(layoutInflater.inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList?.get(position)
        holder.render(item, holder, context, onClickListener, onClickBookmark)
    }

    override fun getItemCount(): Int = newsList.size
}