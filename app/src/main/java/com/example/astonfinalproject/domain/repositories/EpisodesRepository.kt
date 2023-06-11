package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.EpisodeList

interface EpisodesRepository {
    suspend fun getAllEpisodes(page: Int): EpisodeList
    suspend fun filterEpisodes(
        name: String?,
        episode: String?
    ): EpisodeList
}