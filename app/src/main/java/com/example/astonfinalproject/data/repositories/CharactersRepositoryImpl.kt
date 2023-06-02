package com.example.astonfinalproject.data.repositories

import com.example.astonfinalproject.data.db.ApplicationDatabase
import com.example.astonfinalproject.data.network.CharactersService
import com.example.astonfinalproject.domain.entities.CharacterEntity
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(
    private val charactersService: CharactersService,
    private val db: ApplicationDatabase
) : CharactersRepository {

    override suspend fun getCharactersByPage(
        page: Int
    ) = flow {
        emit(charactersService.getCharactersByPage(page))
    }

    override suspend fun filterCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ) = flow {
        emit(charactersService.filterCharacters(name, status, species, type, gender))
    }

    override suspend fun getCharactersByPageFromDatabase(page: Int): Flow<List<CharacterEntity>> =
        db.charactersDao().getCharactersByPage(page)

    override suspend fun insertAllCharactersIntoDatabase(characters: List<CharacterEntity>) =
        db.charactersDao().insertAll(characters)
}