package com.example.astonfinalproject.presentation.screens.characters

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharactersSearchBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.CharacterItemAdapter
import com.example.astonfinalproject.presentation.viewmodels.characters.CharactersSearchViewModel
import kotlinx.coroutines.launch

class CharactersSearchFragment : BaseFragment<FragmentCharactersSearchBinding, CharactersSearchViewModel>(
    R.layout.fragment_characters_search,
    CharactersSearchViewModel::class.java
) {
    override fun createBinding(view: View): FragmentCharactersSearchBinding {
        return FragmentCharactersSearchBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterSearchName = arguments?.getString(CHARACTERS_SEARCH_NAME_KEY)
        val characterSearchStatus = arguments?.getString(CHARACTERS_SEARCH_STATUS_KEY)
        val characterSearchSpecies = arguments?.getString(CHARACTERS_SEARCH_SPECIES_KEY)
        val characterSearchType = arguments?.getString(CHARACTERS_SEARCH_TYPE_KEY)
        val characterSearchGender = arguments?.getString(CHARACTERS_SEARCH_GENDER_KEY)

        val adapter = CharacterItemAdapter(
            characters = listOf(),
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
        binding.charactersSearchRecyclerView.adapter = adapter
        viewModel.filterCharacters(
            name = characterSearchName,
            status = characterSearchStatus,
            species = characterSearchSpecies,
            type = characterSearchType,
            gender = characterSearchGender
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterList
                    .collect { characters ->
                        adapter.characters = characters
                        adapter.notifyDataSetChanged()
                    }
            }
        }
    }

    companion object {
        const val CHARACTERS_SEARCH_NAME_KEY = "name"
        const val CHARACTERS_SEARCH_STATUS_KEY = "status"
        const val CHARACTERS_SEARCH_SPECIES_KEY = "species"
        const val CHARACTERS_SEARCH_TYPE_KEY = "type"
        const val CHARACTERS_SEARCH_GENDER_KEY = "gender"
    }
}