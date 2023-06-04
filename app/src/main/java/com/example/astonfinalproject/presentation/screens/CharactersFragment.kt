package com.example.astonfinalproject.presentation.screens

import android.os.Bundle
import android.view.View
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharactersBinding
import com.example.astonfinalproject.presentation.BaseFragment

class CharactersFragment: BaseFragment<FragmentCharactersBinding>(R.layout.fragment_characters) {

    override fun createBinding(view: View): FragmentCharactersBinding {
        return FragmentCharactersBinding.bind(view)
    }
}