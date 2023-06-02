package com.example.astonfinalproject.presentation.screens.locations

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentLocationDetailsBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.domain.entities.Location
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.CharacterItemAdapter
import com.example.astonfinalproject.presentation.screens.characters.CharacterDetailsFragment
import com.example.astonfinalproject.presentation.viewmodels.locations.LocationDetailsViewModel
import kotlinx.coroutines.launch

class LocationDetailsFragment: BaseFragment<FragmentLocationDetailsBinding, LocationDetailsViewModel>(
    R.layout.fragment_location_details,
    LocationDetailsViewModel::class.java
) {
    override fun createBinding(view: View): FragmentLocationDetailsBinding {
        return FragmentLocationDetailsBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationId = arguments?.getInt(LOCATION_ID_KEY)
        val adapter = CharacterItemAdapter(
            characters = mutableListOf(),
            onItemClickAction = { characterId ->
                val detailsFragment = CharacterDetailsFragment().apply {
                    this.arguments =
                        bundleOf(CharacterDetailsFragment.CHARACTER_ID_KEY to characterId)
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.locationDetailsCharactersRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSingleLocation(locationId!!).collect {
                    bindLocation(it)
                    viewModel.getLocationCharacters(it.residents)
                }
                viewModel.locationCharacters.collect {
                    adapter.characters = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun bindLocation(location: Location) {
        binding.locationDetailsIdTextView.text = location.id.toString()
        binding.locationDetailsNameTextView.text = location.name
        binding.locationDetailsTypeTextView.text = location.type
        binding.locationDetailsDimensionTextView.text = location.dimension
        binding.locationDetailsCreatedTextView.text = location.created
    }

    companion object {
        const val LOCATION_ID_KEY = "location_id"
    }
}