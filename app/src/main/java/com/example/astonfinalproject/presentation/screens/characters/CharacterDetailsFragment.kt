package com.example.astonfinalproject.presentation.screens.characters

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentCharacterDetailsBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.EpisodeItemAdapter
import com.example.astonfinalproject.presentation.screens.episodes.EpisodesDetailsFragment
import com.example.astonfinalproject.presentation.viewmodels.characters.CharacterDetailsViewModel
import kotlinx.coroutines.launch

class CharacterDetailsFragment: BaseFragment<FragmentCharacterDetailsBinding, CharacterDetailsViewModel>(
    R.layout.fragment_character_details,
    CharacterDetailsViewModel::class.java
){
    override fun createBinding(view: View): FragmentCharacterDetailsBinding {
        return FragmentCharacterDetailsBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterId = arguments?.getInt(CHARACTER_ID_KEY)
        super.onViewCreated(view, savedInstanceState)
        val adapter = EpisodeItemAdapter(
            episodes = listOf(),
            onItemClickAction = {
                val episodesDetailsFragment = EpisodesDetailsFragment().apply {
                    arguments = bundleOf(
                        EpisodesDetailsFragment.EPISODE_ID_KEY to it
                    )
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, episodesDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.characterDetailsEpisodesRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSingleCharacter(characterId!!).collect {
                    bindCharacter(it)
                    viewModel.getCharacterEpisodes(it.episodesUrlList)
                }
                viewModel.characterEpisodes.collect {
                    adapter.episodes = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun bindCharacter(character: Character) {
        with(character) {
            binding.characterDetailsIdTextView.text = id.toString()
            binding.characterDetailsNameTextView.text = name
            binding.characterDetailsStatusTextView.text = status
            binding.characterDetailsSpeciesTextView.text = species
            binding.characterDetailsTypeTextView.text = type
            binding.characterDetailsOriginTextView.text = origin.name
            binding.characterDetailsLocationTextView.text = location.name
            binding.characterDetailsImageView.load(imageUrl)
            binding.characterDetailsCreatedTextView.text = created
        }
    }

    companion object {
        const val CHARACTER_ID_KEY = "character_id"
    }
}