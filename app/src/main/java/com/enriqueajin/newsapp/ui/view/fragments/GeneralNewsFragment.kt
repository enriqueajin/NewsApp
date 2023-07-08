package com.enriqueajin.newsapp.ui.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.databinding.FragmentGeneralNewsBinding
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.ui.view.adapters.NewsAdapter
import com.enriqueajin.newsapp.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralNewsFragment : Fragment() {

    private lateinit var binding: FragmentGeneralNewsBinding
    private lateinit var adapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralNewsBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        // Fake data
//        val art1 = Article("Enrique Ajin", "How to spend money", "This is a 2022 guide...", "", "https://cdn.i-scmp.com/sites/default/files/styles/landscape/public/d8/images/canvas/2022/09/15/04964401-037d-434c-88d4-765f2e8ddd1f_5b91181c.jpg?itok=AxQ-gGmm&v=1663223815", "Nov 23")
//        val art2 = Article("Anita Noriega", "What do you need?", "This is a 2022 guide...", "", "https://cdn.i-scmp.com/sites/default/files/styles/landscape/public/d8/images/canvas/2022/09/15/04964401-037d-434c-88d4-765f2e8ddd1f_5b91181c.jpg?itok=AxQ-gGmm&v=1663223815", "Nov 23")
//        val art3 = Article("Ivette López", "What does you bed feels like?", "This is a 2022 guide...", "", "https://cdn.i-scmp.com/sites/default/files/styles/landscape/public/d8/images/canvas/2022/09/15/04964401-037d-434c-88d4-765f2e8ddd1f_5b91181c.jpg?itok=AxQ-gGmm&v=1663223815", "Nov 23")
//        val newsList = mutableListOf(art1, art2, art3)

        newsViewModel.getNewsByCategory("general")
        newsViewModel.news.observe(viewLifecycleOwner) { news ->
            if (news != null) {
                adapter = NewsAdapter(
                    news,
                    requireContext(),
                    onClickListener = { Log.d("TAG", "you've clicked the card") },
                    onClickBookmark = { Log.d("TAG", "you've clicked the bookmark") }
                )
                binding.rvGeneralNews.adapter = adapter
                binding.rvGeneralNews.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    companion object {

//        fun newInstance() =
//            GeneralNewsFragment().apply {
//
//            }

        fun newInstance() = GeneralNewsFragment()
    }
}