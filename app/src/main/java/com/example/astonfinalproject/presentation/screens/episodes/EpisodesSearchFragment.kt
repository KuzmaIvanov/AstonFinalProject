package com.example.astonfinalproject.presentation.screens.episodes

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentEpisodesSearchBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.EpisodeItemAdapter
import com.example.astonfinalproject.presentation.viewmodels.episodes.EpisodesSearchViewModel
import kotlinx.coroutines.launch

class EpisodesSearchFragment: BaseFragment<FragmentEpisodesSearchBinding, EpisodesSearchViewModel>(
    R.layout.fragment_episodes_search,
    EpisodesSearchViewModel::class.java
) {
    override fun createBinding(view: View): FragmentEpisodesSearchBinding {
        return FragmentEpisodesSearchBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val episodesSearchName = arguments?.getString(EPISODES_SEARCH_NAME)
        val episodesSearchEpisode = arguments?.getString(EPISODES_SEARCH_EPISODE)

        val adapter = EpisodeItemAdapter(
            episodes = listOf(),
            onItemClickAction = {

            }
        )
        viewModel.filterEpisodes(episodesSearchName, episodesSearchEpisode)
        binding.episodesSearchRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episodesList
                    .collect { episodes ->
                        adapter.episodes = episodes
                        adapter.notifyDataSetChanged()
                    }
            }
        }
    }

    companion object {
        const val EPISODES_SEARCH_NAME = "episodes_name"
        const val EPISODES_SEARCH_EPISODE = "episodes_episode"
    }
}