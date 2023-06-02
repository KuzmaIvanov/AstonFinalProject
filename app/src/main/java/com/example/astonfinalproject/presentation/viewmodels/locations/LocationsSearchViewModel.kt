package com.example.astonfinalproject.presentation.viewmodels.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.domain.entities.Location
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsSearchViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository
): ViewModel() {

    private val _locationList = MutableStateFlow<List<Location>>(listOf())
    val locationList: StateFlow<List<Location>> = _locationList

    fun filterLocations(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ) {
        viewModelScope.launch {
            locationsRepository.filterLocations(name, type, dimension)
                .catch {

                }
                .collect {
                    val newLocationList = _locationList.value.toMutableList().apply {
                        addAll(it.locations)
                    }
                    _locationList.value = newLocationList
                }
        }
    }
}