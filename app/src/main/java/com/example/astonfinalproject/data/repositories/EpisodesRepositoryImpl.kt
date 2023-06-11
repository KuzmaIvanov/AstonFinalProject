package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.network.RickAndMortyService
import com.example.astonfinalproject.domain.entities.EpisodeList
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodesRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
): EpisodesRepository {

    override suspend fun getAllEpisodes(
        page: Int
    ): EpisodeList = rickAndMortyService.getAllEpisodes(page)

    override suspend fun filterEpisodes(
        name: String?,
        episode: String?
    ): EpisodeList = rickAndMortyService.filterEpisodes(name, episode)
}