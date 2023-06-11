package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.network.RickAndMortyService
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : CharactersRepository {

    override suspend fun getAllCharacters(
        page: Int
    ) = rickAndMortyService.getAllCharacters(page)

    override suspend fun filterCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ) = rickAndMortyService.filterCharacters(name, status, species, type, gender)
}