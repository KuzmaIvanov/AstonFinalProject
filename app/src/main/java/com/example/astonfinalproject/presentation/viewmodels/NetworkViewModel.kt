package com.example.astonfinalproject.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.network.NetworkUiState
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val locationsRepository: LocationsRepository,
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val _characterListUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Empty)
    val characterListUiState: StateFlow<NetworkUiState> = _characterListUiState

    private val _locationListUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Empty)
    val locationListUiState: StateFlow<NetworkUiState> = _locationListUiState

    private val _episodesListUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Empty)
    val episodesListUiState: StateFlow<NetworkUiState> = _episodesListUiState

    init {
        getAllCharacters()
        getAllLocations()
        getAllEpisodes()
    }

    fun getAllCharacters(page: Int = 1) {
        viewModelScope.launch {
            flow {
                emit(charactersRepository.getAllCharacters(page))
            }
                .catch {
                    _characterListUiState.value = NetworkUiState.Error(it)
                }
                .collect { characterList ->
                    _characterListUiState.value = NetworkUiState.Success(characterList)
                }
        }
    }

    fun filterCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) {
        Log.i("TEST", "FILTER FUNC STARTED!")
        viewModelScope.launch {
            flow {
                emit(charactersRepository.filterCharacters(name, status, species, type, gender))
            }
                .catch {
                    _characterListUiState.value = NetworkUiState.Error(it)
                }
                .collect { characterList ->
                    _characterListUiState.value = NetworkUiState.Success(characterList)
                }
        }
    }

    fun getAllLocations(page: Int = 1) {
        viewModelScope.launch {
            flow {
                emit(locationsRepository.getAllLocations(page))
            }
                .catch {
                    _locationListUiState.value = NetworkUiState.Error(it)
                }
                .collect { characterList ->
                    _locationListUiState.value = NetworkUiState.Success(characterList)
                }
        }
    }

    fun filterLocations(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ) {
        viewModelScope.launch {
            flow {
                emit(locationsRepository.filterLocations(name, type, dimension))
            }
                .catch {
                    _locationListUiState.value = NetworkUiState.Error(it)
                }
                .collect {  locationList ->
                    _locationListUiState.value = NetworkUiState.Success(locationList)
                }
        }
    }

    fun getAllEpisodes(page: Int = 1) {
        viewModelScope.launch {
            flow {
                emit(episodesRepository.getAllEpisodes(page))
            }
                .catch {
                    _episodesListUiState.value = NetworkUiState.Error(it)
                }
                .collect {
                    _episodesListUiState.value = NetworkUiState.Success(it)
                }
        }
    }

    fun filterEpisodes(
        name: String? = null,
        episode: String? = null
    ) {
        viewModelScope.launch {
            flow {
                emit(episodesRepository.filterEpisodes(name, episode))
            }
                .catch {
                    _episodesListUiState.value = NetworkUiState.Error(it)
                }
                .collect {
                    _episodesListUiState.value = NetworkUiState.Success(it)
                }
        }
    }
}