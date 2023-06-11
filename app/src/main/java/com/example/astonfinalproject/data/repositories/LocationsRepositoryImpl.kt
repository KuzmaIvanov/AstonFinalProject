package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.network.RickAndMortyService
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import com.example.astonfinalproject.domain.entities.LocationList
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
): LocationsRepository {

    override suspend fun getAllLocations(
        page: Int
    ): LocationList = rickAndMortyService.getAllLocations(page)

    override suspend fun filterLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): LocationList = rickAndMortyService.filterLocations(name, type, dimension)
}