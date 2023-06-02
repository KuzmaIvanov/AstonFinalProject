package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.network.LocationsService
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationsService: LocationsService
): LocationsRepository {

    override suspend fun getLocationsByPage(
        page: Int
    ) = flow {
        emit(locationsService.getLocationsByPage(page))
    }

    override suspend fun filterLocations(
        name: String?,
        type: String?,
        dimension: String?
    ) = flow {
        emit(locationsService.filterLocations(name, type, dimension))
    }
}