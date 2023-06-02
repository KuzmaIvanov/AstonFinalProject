package com.example.astonfinalproject.presentation.screens.episodes

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentEpisodesDetailsBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.CharacterItemAdapter
import com.example.astonfinalproject.presentation.screens.characters.CharacterDetailsFragment
import com.example.astonfinalproject.presentation.viewmodels.episodes.EpisodesDetailsViewModel
import kotlinx.coroutines.launch

class EpisodesDetailsFragment: BaseFragment<FragmentEpisodesDetailsBinding, EpisodesDetailsViewModel>(
    R.layout.fragment_episodes_details,
    EpisodesDetailsViewModel::class.java
) {
    override fun createBinding(view: View): FragmentEpisodesDetailsBinding {
        return FragmentEpisodesDetailsBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val episodeId = arguments?.getInt(EPISODE_ID_KEY)
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
        binding.episodesDetailsCharactersRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSingleEpisode(episodeId!!).collect {
                    bindEpisode(it)
                    viewModel.getAllEpisodeCharacters(it.characters)
                }
                viewModel.episodeCharacters.collect {
                    adapter.characters = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun bindEpisode(episode: Episode) {
        binding.episodesDetailsIdTextView.text = episode.id.toString()
        binding.episodesDetailsNameTextView.text = episode.name
        binding.episodesDetailsAirDateTextView.text = episode.airDate
        binding.episodesDetailsEpisodeTextView.text = episode.episode
        binding.episodesDetailsCreatedTextView.text = episode.created
    }

    companion object {
        const val EPISODE_ID_KEY = "episode_id"
    }
}