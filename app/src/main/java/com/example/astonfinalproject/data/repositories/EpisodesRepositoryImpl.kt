package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.network.EpisodesService
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodesRepositoryImpl @Inject constructor(
    private val episodesService: EpisodesService
): EpisodesRepository {

    override suspend fun getEpisodesByPage(
        page: Int
    ) = flow {
        emit(episodesService.getEpisodesByPage(page))
    }

    override suspend fun filterEpisodes(
        name: String?,
        episode: String?
    ) = flow {
        emit(episodesService.filterEpisodes(name, episode))
    }
}