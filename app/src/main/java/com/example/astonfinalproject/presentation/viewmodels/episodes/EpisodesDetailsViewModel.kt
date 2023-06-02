package com.example.astonfinalproject.presentation.viewmodels.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.domain.repositories.DetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
): ViewModel() {

    private val _episodeCharacters = MutableStateFlow<List<Character>>(listOf())
    val episodeCharacters: StateFlow<List<Character>> = _episodeCharacters

    fun getAllEpisodeCharacters(charactersUrl: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.getAllEpisodeCharacters(charactersUrl)
                .catch {

                }
                .collect {
                    _episodeCharacters.value = it
                }
        }
    }

    suspend fun getSingleEpisode(id: Int) = detailsRepository
        .getSingleEpisode(id)
        .flowOn(Dispatchers.IO)
}