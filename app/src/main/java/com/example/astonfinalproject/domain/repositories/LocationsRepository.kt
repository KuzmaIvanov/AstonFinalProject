package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.LocationListResponse
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    suspend fun getLocationsByPage(page: Int): Flow<LocationListResponse>
    suspend fun filterLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<LocationListResponse>
}