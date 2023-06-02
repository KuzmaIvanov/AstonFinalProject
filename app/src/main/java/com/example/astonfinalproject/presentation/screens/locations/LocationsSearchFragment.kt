package com.example.astonfinalproject.presentation.screens.locations

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentLocationSearchBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.LocationItemAdapter
import com.example.astonfinalproject.presentation.viewmodels.locations.LocationsSearchViewModel
import kotlinx.coroutines.launch

class LocationsSearchFragment :
    BaseFragment<FragmentLocationSearchBinding, LocationsSearchViewModel>(
        R.layout.fragment_location_search,
        LocationsSearchViewModel::class.java
    ) {
    override fun createBinding(view: View): FragmentLocationSearchBinding {
        return FragmentLocationSearchBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationSearchName = arguments?.getString(LOCATION_SEARCH_NAME)
        val locationSearchType = arguments?.getString(LOCATION_SEARCH_TYPE)
        val locationSearchDimension = arguments?.getString(LOCATION_SEARCH_DIMENSION)

        val adapter = LocationItemAdapter(
            locations = listOf(),
            onItemClickAction = {
                val locationDetailsFragment = LocationDetailsFragment().apply {
                    arguments = bundleOf(LocationDetailsFragment.LOCATION_ID_KEY to it)
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, locationDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.locationSearchRecyclerView.adapter = adapter
        viewModel.filterLocations(locationSearchName, locationSearchType, locationSearchDimension)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationList.collect { locations ->
                    adapter.locations = locations
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        const val LOCATION_SEARCH_NAME = "location_name"
        const val LOCATION_SEARCH_TYPE = "location_type"
        const val LOCATION_SEARCH_DIMENSION = "location_dimension"
    }
}