package com.example.astonfinalproject.data.network

import com.example.astonfinalproject.domain.entities.LocationListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationsService {

    @GET("location")
    suspend fun getLocationsByPage(
        @Query("page") page: Int
    ): LocationListResponse

    @GET("location")
    suspend fun filterLocations(
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("dimension") dimension: String?
    ): LocationListResponse
}