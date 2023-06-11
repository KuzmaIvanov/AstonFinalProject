package com.example.astonfinalproject.data.network

import com.example.astonfinalproject.domain.entities.CharacterList
import com.example.astonfinalproject.domain.entities.EpisodeList
import com.example.astonfinalproject.domain.entities.LocationList
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CharacterList

    @GET("character")
    suspend fun filterCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): CharacterList

    @GET("location")
    suspend fun getAllLocations(
        @Query("page") page: Int
    ): LocationList

    @GET("location")
    suspend fun filterLocations(
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("dimension") dimension: String?
    ): LocationList

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") page: Int
    ): EpisodeList

    @GET("episode")
    suspend fun filterEpisodes(
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): EpisodeList
}