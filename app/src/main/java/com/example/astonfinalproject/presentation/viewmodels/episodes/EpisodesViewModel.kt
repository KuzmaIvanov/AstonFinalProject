package com.example.astonfinalproject.presentation.viewmodels.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.ResourceWrapper
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {

    private val _episodesList = MutableStateFlow<List<Episode>>(listOf())
    val episodesList: StateFlow<List<Episode>> = _episodesList

    private var currentPage = resourceWrapper.getPageFromSharedPreferences(EPISODES_PAGE_KEY)
    private var totalPages = Int.MAX_VALUE

    init {
        getAllEpisodes()
    }

    private fun getAllEpisodes() {
        viewModelScope.launch {
            for (page in 1..currentPage) {
                episodesRepository.getEpisodesByPage(page)
                    .collect {
                        totalPages = it.info.pages
                        val newLocationList = _episodesList.value.toMutableList().apply {
                            addAll(it.episodes)
                        }
                        _episodesList.value = newLocationList
                    }
            }
        }
    }

    fun loadNextEpisodes() {
        val nextPage = currentPage + 1
        if (nextPage <= totalPages) {
            viewModelScope.launch {
                episodesRepository.getEpisodesByPage(nextPage)
                    .collect {
                        currentPage = nextPage
                        totalPages = it.info.pages
                        val newLocationList = _episodesList.value.toMutableList().apply {
                            addAll(it.episodes)
                        }
                        _episodesList.value = newLocationList
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        resourceWrapper.savePageIntoSharedPreferences(EPISODES_PAGE_KEY, currentPage)
    }

    companion object {
        const val EPISODES_PAGE_KEY = "episodesKey"
    }
}