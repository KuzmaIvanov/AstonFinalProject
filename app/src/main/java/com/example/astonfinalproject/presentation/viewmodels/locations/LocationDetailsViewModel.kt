package com.example.astonfinalproject.presentation.viewmodels.locations

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

class LocationDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
): ViewModel() {

    private val _locationCharacters = MutableStateFlow<List<Character>>(listOf())
    val locationCharacters: StateFlow<List<Character>> = _locationCharacters

    fun getLocationCharacters(charactersUrl: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.getAllLocationCharacters(charactersUrl)
                .catch {

                }
                .collect {
                    _locationCharacters.value = it
                }
        }
    }

    suspend fun getSingleLocation(id: Int) = detailsRepository
        .getSingleLocation(id)
        .flowOn(Dispatchers.IO)
}