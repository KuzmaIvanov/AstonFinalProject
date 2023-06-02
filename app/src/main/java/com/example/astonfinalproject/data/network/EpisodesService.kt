package com.example.astonfinalproject.data.network

import com.example.astonfinalproject.domain.entities.EpisodeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesService {

    @GET("episode")
    suspend fun getEpisodesByPage(
        @Query("page") page: Int
    ): EpisodeListResponse

    @GET("episode")
    suspend fun filterEpisodes(
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): EpisodeListResponse
}