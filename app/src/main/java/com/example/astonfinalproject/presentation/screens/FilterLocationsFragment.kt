package com.example.astonfinalproject.presentation.screens

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.astonfinalproject.MyApplication
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentFilterLocationsBinding
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.viewmodels.NetworkViewModel
import javax.inject.Inject

class FilterLocationsFragment: BaseFragment<FragmentFilterLocationsBinding>(R.layout.fragment_filter_locations) {

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun createBinding(view: View): FragmentFilterLocationsBinding {
        return FragmentFilterLocationsBinding.bind(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_top_app_bar_apply -> {
                networkViewModel.filterLocations(
                    name = binding.filterLocationsNameEditText.text.toString(),
                    type = binding.filterLocationsTypeEditText.text.toString(),
                    dimension = binding.filterLocationsDimensionEditText.text.toString()
                )
                parentFragmentManager.popBackStack()
                true
            }
            else -> false
        }
    }
}