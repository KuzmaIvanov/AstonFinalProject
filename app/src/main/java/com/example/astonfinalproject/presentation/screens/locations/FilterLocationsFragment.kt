package com.example.astonfinalproject.presentation.screens.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentFilterLocationsBinding

class FilterLocationsFragment: Fragment() {

    private var _binding: FragmentFilterLocationsBinding? = null
    private val binding get() =  requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_top_app_bar_apply -> {
                val locationSearchFragment = LocationsSearchFragment().apply {
                    arguments = getBundleForLocationSearchFragment()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, locationSearchFragment)
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBundleForLocationSearchFragment(): Bundle {
        return bundleOf(
            LocationsSearchFragment.LOCATION_SEARCH_NAME to binding.filterLocationsNameEditText.text.toString(),
            LocationsSearchFragment.LOCATION_SEARCH_TYPE to binding.filterLocationsTypeEditText.text.toString(),
            LocationsSearchFragment.LOCATION_SEARCH_DIMENSION to binding.filterLocationsDimensionEditText.text.toString()
        )
    }
}