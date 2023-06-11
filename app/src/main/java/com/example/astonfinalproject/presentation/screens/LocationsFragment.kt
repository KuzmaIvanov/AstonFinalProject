package com.example.astonfinalproject.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.astonfinalproject.MyApplication
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.network.NetworkUiState
import com.example.astonfinalproject.databinding.FragmentLocationsBinding
import com.example.astonfinalproject.domain.entities.LocationList
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.LocationItemAdapter
import com.example.astonfinalproject.presentation.viewmodels.NetworkViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsFragment: BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun createBinding(view: View): FragmentLocationsBinding {
        return FragmentLocationsBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LocationItemAdapter(listOf())
        binding.locationRecyclerView.adapter = adapter
        binding.locationRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        lifecycleScope.launch {
            networkViewModel.locationListUiState
                .flowWithLifecycle(lifecycle)
                .collect { uiState ->
                    when (uiState) {
                        is NetworkUiState.Success<*> -> {
                            adapter.locations = (uiState.data as LocationList).locations
                            adapter.notifyDataSetChanged()
                        }

                        is NetworkUiState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Error: ${uiState.message.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
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
                    networkViewModel.filterLocations(name = query)
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