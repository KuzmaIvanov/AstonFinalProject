package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.LocationList

interface LocationsRepository {
    suspend fun getAllLocations(page: Int): LocationList
    suspend fun filterLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): LocationList
}