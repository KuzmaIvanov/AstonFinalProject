package com.example.astonfinalproject.domain.repositories

import com.example.astonfinalproject.domain.entities.CharacterList

interface CharactersRepository {
    suspend fun getAllCharacters(page: Int): CharacterList
    suspend fun filterCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterList
}