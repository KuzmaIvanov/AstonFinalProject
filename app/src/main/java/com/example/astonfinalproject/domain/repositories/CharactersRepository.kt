package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.CharacterEntity
import com.example.astonfinalproject.domain.entities.CharacterListResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharactersByPage(page: Int): Flow<CharacterListResponse>
    suspend fun filterCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<CharacterListResponse>

    suspend fun getCharactersByPageFromDatabase(page: Int): Flow<List<CharacterEntity>>
    suspend fun insertAllCharactersIntoDatabase(characters: List<CharacterEntity>)
}