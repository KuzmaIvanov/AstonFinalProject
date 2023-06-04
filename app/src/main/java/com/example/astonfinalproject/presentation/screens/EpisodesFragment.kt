package com.example.astonfinalproject.presentation.screens

import android.view.View
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentEpisodesBinding
import com.example.astonfinalproject.presentation.BaseFragment

class EpisodesFragment: BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    override fun createBinding(view: View): FragmentEpisodesBinding {
        return FragmentEpisodesBinding.bind(view)
    }
}