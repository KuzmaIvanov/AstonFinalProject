package com.example.astonfinalproject.presentation.viewmodels.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.domain.repositories.DetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
): ViewModel() {

    private val _characterEpisodes = MutableStateFlow<List<Episode>>(mutableListOf())
    val characterEpisodes: StateFlow<List<Episode>> = _characterEpisodes

    fun getCharacterEpisodes(episodeUrls: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.getAllCharacterEpisodes(episodeUrls)
                .catch {

                }
                .collect {
                    _characterEpisodes.value = it
                }
        }
    }

    suspend fun getSingleCharacter(id: Int) = detailsRepository
        .getSingleCharacter(id)
        .flowOn(Dispatchers.IO)
}