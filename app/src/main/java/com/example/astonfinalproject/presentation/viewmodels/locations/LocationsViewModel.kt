package com.example.astonfinalproject.presentation.viewmodels.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.ResourceWrapper
import com.example.astonfinalproject.domain.entities.Location
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository,
    private val resourceWrapper: ResourceWrapper
): ViewModel(){

    private val _locationList = MutableStateFlow<List<Location>>(listOf())
    val locationList: StateFlow<List<Location>> = _locationList

    private var currentPage = resourceWrapper.getPageFromSharedPreferences(LOCATIONS_PAGE_KEY)
    private var totalPages = Int.MAX_VALUE

    init {
        getAllLocations()
    }

    private fun getAllLocations() {
        viewModelScope.launch {
            for (page in 1..currentPage) {
                locationsRepository.getLocationsByPage(page)
                    .collect {
                        totalPages = it.info.pages
                        val newLocationList = _locationList.value.toMutableList().apply {
                            addAll(it.locations)
                        }
                        _locationList.value = newLocationList
                    }
            }
        }
    }

    fun loadNextLocations() {
        val nextPage = currentPage + 1
        if (nextPage <= totalPages) {
            viewModelScope.launch {
                locationsRepository.getLocationsByPage(nextPage)
                    .collect {
                        currentPage = nextPage
                        totalPages = it.info.pages
                        val newLocationList = _locationList.value.toMutableList().apply {
                            addAll(it.locations)
                        }
                        _locationList.value = newLocationList
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        resourceWrapper.savePageIntoSharedPreferences(LOCATIONS_PAGE_KEY, currentPage)
    }

    companion object {
        const val LOCATIONS_PAGE_KEY = "locationsPage"
    }
}