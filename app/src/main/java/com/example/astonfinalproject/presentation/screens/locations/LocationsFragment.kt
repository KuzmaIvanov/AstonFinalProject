package com.example.astonfinalproject.presentation.screens.locations

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentLocationsBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.LocationItemAdapter
import com.example.astonfinalproject.presentation.pagination.RecyclerViewGridLayoutScrollListener
import com.example.astonfinalproject.presentation.viewmodels.locations.LocationsViewModel
import kotlinx.coroutines.launch

class LocationsFragment: BaseFragment<FragmentLocationsBinding, LocationsViewModel>(
    R.layout.fragment_locations,
    LocationsViewModel::class.java
) {

    override fun createBinding(view: View): FragmentLocationsBinding {
        return FragmentLocationsBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.locationRecyclerView.adapter = adapter
        binding.locationRecyclerView.addOnScrollListener(RecyclerViewGridLayoutScrollListener{
            viewModel.loadNextLocations()
        })
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationList
                    .collect {  locations->
                        adapter.locations = locations
                        adapter.notifyDataSetChanged()
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        val searchViewItem = menu.findItem(R.id.search_bar)
        val searchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrEmpty()) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container_view,
                            LocationsSearchFragment()
                                .apply {
                                    arguments = bundleOf(
                                        LocationsSearchFragment.LOCATION_SEARCH_NAME to query
                                    )
                                }
                        )
                        .addToBackStack(null)
                        .commit()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_bar -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, FilterLocationsFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> false
        }
    }
}