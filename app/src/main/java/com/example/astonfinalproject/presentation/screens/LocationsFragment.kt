package com.example.astonfinalproject.presentation.screens

import android.view.View
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentLocationsBinding
import com.example.astonfinalproject.presentation.BaseFragment

class LocationsFragment: BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    override fun createBinding(view: View): FragmentLocationsBinding {
        return FragmentLocationsBinding.bind(view)
    }
}