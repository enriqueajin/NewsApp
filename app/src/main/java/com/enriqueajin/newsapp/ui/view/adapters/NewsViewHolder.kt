package com.enriqueajin.newsapp.ui.view.adapters

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.databinding.NewsItemBinding
import com.enriqueajin.newsapp.domain.model.Article

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = NewsItemBinding.bind(view)
    private var isBookmarkActive = false // this flag comes from the database that store the bookmarks

    fun render(
        article: Article,
        holder: NewsViewHolder,
        context: Context,
        onClickListener: (Article) -> Unit,
        onClickBookmark: (Int) -> Unit
    ) {
        binding.tvNewsAuthor.text = article.author
        binding.tvNewsTitle.text = article.title
        binding.tvNewsDate.text = article.publishedAt
        Glide.with(binding.ivNews.context).load(article.urlToImage).into(binding.ivNews)
        binding.ibBookmark.setOnClickListener {
            val bookmarkIcon = holder.binding.ibBookmark
            handleBookmarkAnimations(bookmarkIcon, context)
            toggleBookmarkIcon(isBookmarkActive, bookmarkIcon)
            onClickBookmark(adapterPosition)
        }
        binding.cardContainer.setOnClickListener { onClickListener(article) }
    }

    private fun toggleBookmarkIcon(isBookMarkActive: Boolean, bookmarkIcon: ImageButton) {
        bookmarkIcon.setImageDrawable(
            when(isBookMarkActive) {
                false -> {
                    this.isBookmarkActive = true
                    bookmarkIcon.context.getDrawable(R.drawable.ic_active_bookmark)
                }
                true -> {
                    this.isBookmarkActive = false
                    bookmarkIcon.context.getDrawable(R.drawable.ic_inactive_bookmark)
                }
            }
        )
    }

    private fun handleBookmarkAnimations(bookmarkIcon: ImageButton, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.circle_animation)
        bookmarkIcon.startAnimation(animation)
    }
}