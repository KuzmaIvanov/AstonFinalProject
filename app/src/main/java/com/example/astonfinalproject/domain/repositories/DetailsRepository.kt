package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.domain.entities.Location
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getSingleCharacter(id: Int): Flow<Character>
    suspend fun getAllCharacterEpisodes(episodesUrl: List<String>): Flow<List<Episode>>

    suspend fun getSingleLocation(id: Int): Flow<Location>
    suspend fun getAllLocationCharacters(charactersUrl: List<String>): Flow<List<Character>>

    suspend fun getSingleEpisode(id: Int): Flow<Episode>
    suspend fun getAllEpisodeCharacters(charactersUrl: List<String>): Flow<List<Character>>
}