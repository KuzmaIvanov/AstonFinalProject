package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.EpisodeListResponse
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    suspend fun getEpisodesByPage(page: Int): Flow<EpisodeListResponse>
    suspend fun filterEpisodes(
        name: String?,
        episode: String?
    ): Flow<EpisodeListResponse>
}