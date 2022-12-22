package com.enriqueajin.newsapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.databinding.FragmentGeneralNewsBinding


class GeneralNewsFragment : Fragment() {

    private lateinit var binding: FragmentGeneralNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

//        fun newInstance() =
//            GeneralNewsFragment().apply {
//
//            }

        fun newInstance() = GeneralNewsFragment()
    }
}