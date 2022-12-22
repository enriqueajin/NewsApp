package com.enriqueajin.newsapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.data.utils.Constants
import com.enriqueajin.newsapp.databinding.FragmentMainNewsBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainNewsFragment : Fragment() {

    private lateinit var binding: FragmentMainNewsBinding
    private lateinit var adapter: NewsViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainNewsBinding.inflate(inflater, container, false)
        adapter = NewsViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = Constants.NEWS_CATEGORIES[position]
        }.attach()
        return binding.root
    }
}

class NewsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
//        return Constants.NEWS_CATEGORIES.size
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> GeneralNewsFragment.newInstance()
            else -> Fragment()
        }

    }
}