package com.example.astonfinalproject.presentation.viewmodels.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesSearchViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository
): ViewModel() {

    private val _episodesList= MutableStateFlow<List<Episode>>(listOf())
    val episodesList: StateFlow<List<Episode>> = _episodesList

    fun filterEpisodes(
        name: String? = null,
        episode: String? = null
    ) {
        viewModelScope.launch {
            episodesRepository.filterEpisodes(name, episode)
                .catch {

                }
                .collect {
                    val newEpisodesList = _episodesList.value.toMutableList().apply {
                        addAll(it.episodes)
                    }
                    _episodesList.value = newEpisodesList
                }
        }
    }
}