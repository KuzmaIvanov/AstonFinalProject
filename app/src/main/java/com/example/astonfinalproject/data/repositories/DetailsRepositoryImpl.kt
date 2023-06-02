package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.network.DetailsService
import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.domain.entities.Location
import com.example.astonfinalproject.domain.repositories.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepositoryImpl @Inject constructor(
    private val detailsService: DetailsService
): DetailsRepository {
    override suspend fun getSingleCharacter(id: Int) = flow {
        emit(detailsService.getSingleCharacter(id))
    }

    override suspend fun getAllCharacterEpisodes(episodesUrl: List<String>) = flow {
        val episodes = mutableListOf<Episode>()
        episodesUrl.forEach {
            episodes.add(detailsService.getSingleEpisodeByUrl(it))
        }
        emit(episodes)
    }

    override suspend fun getSingleLocation(id: Int): Flow<Location> = flow {
        emit(detailsService.getSingleLocation(id))
    }

    override suspend fun getAllLocationCharacters(charactersUrl: List<String>) = flow {
        val characters = mutableListOf<Character>()
        charactersUrl.forEach {
            characters.add(detailsService.getSingleCharacterByUrl(it))
        }
        emit(characters)
    }

    override suspend fun getSingleEpisode(id: Int): Flow<Episode> = flow {
        emit(detailsService.getSingleEpisode(id))
    }

    override suspend fun getAllEpisodeCharacters(charactersUrl: List<String>) = flow {
        val characters = mutableListOf<Character>()
        charactersUrl.forEach {
            characters.add(detailsService.getSingleCharacterByUrl(it))
        }
        emit(characters)
    }
}