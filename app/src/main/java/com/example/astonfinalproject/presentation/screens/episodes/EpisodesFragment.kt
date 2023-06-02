package com.example.astonfinalproject.presentation.screens.episodes

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
import com.example.astonfinalproject.databinding.FragmentEpisodesBinding
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.presentation.BaseFragment
import com.example.astonfinalproject.presentation.adapters.EpisodeItemAdapter
import com.example.astonfinalproject.presentation.pagination.RecyclerViewGridLayoutScrollListener
import com.example.astonfinalproject.presentation.viewmodels.episodes.EpisodesViewModel
import kotlinx.coroutines.launch

class EpisodesFragment : BaseFragment<FragmentEpisodesBinding, EpisodesViewModel>(
    R.layout.fragment_episodes,
    EpisodesViewModel::class.java
) {

    override fun createBinding(view: View): FragmentEpisodesBinding {
        return FragmentEpisodesBinding.bind(view)
    }

    override fun initDaggerComponent(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        binding.episodesRecyclerView.adapter = adapter
        binding.episodesRecyclerView.addOnScrollListener(RecyclerViewGridLayoutScrollListener{
            viewModel.loadNextEpisodes()
        })
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episodesList
                    .collect {  episodes->
                        adapter.episodes = episodes
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
                    val episodesSearchFragment = EpisodesSearchFragment().apply {
                        arguments = bundleOf(EpisodesSearchFragment.EPISODES_SEARCH_NAME to query)
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, episodesSearchFragment)
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
                    .replace(R.id.fragment_container_view, FilterEpisodesFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> false
        }
    }
}