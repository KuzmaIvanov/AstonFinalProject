package com.example.astonfinalproject.presentation.viewmodels.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.ResourceWrapper
import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.domain.entities.CharacterEntity
import com.example.astonfinalproject.domain.entities.CharacterLocation
import com.example.astonfinalproject.domain.entities.CharacterOrigin
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {

    private val _characterList = MutableStateFlow<Result<List<Character>>>(Result.success(listOf()))
    val characterList: StateFlow<Result<List<Character>>> = _characterList

    private var currentPage = resourceWrapper.getPageFromSharedPreferences(CHARACTERS_PAGE_KEY)
    private var totalPages = Int.MAX_VALUE

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            for (page in 1..currentPage) {
                charactersRepository.getCharactersByPage(page)
                    .catch {
                        _characterList.value = Result.failure(it)
                    }
                    .collect { response ->
                        totalPages = response.info.pages
                        val newCharacterList = _characterList.value
                            .getOrThrow()
                            .toMutableList()
                            .apply {
                                addAll(response.characters)
                            }
                        _characterList.value = Result.success(newCharacterList)
                        charactersRepository.insertAllCharactersIntoDatabase(response.characters.map { character ->
                            mapCharacterToCharacterEntity(character, page)
                        })
                    }
            }
        }
    }

    fun loadNextCharacters() {
        val nextPage = currentPage + 1
        if (nextPage <= totalPages) {
            viewModelScope.launch(Dispatchers.IO) {
                charactersRepository.getCharactersByPage(nextPage)
                    .catch {
                        _characterList.value = Result.failure(it)
                    }
                    .collect { response ->
                        currentPage = nextPage
                        totalPages = response.info.pages
                        val newCharacterList = _characterList.value
                            .getOrThrow()
                            .toMutableList()
                            .apply {
                                addAll(response.characters)
                            }
                        _characterList.value = Result.success(newCharacterList)
                        charactersRepository.insertAllCharactersIntoDatabase(response.characters.map { character ->
                            mapCharacterToCharacterEntity(character, currentPage)
                        })
                    }
            }
        }
    }

    private fun mapCharacterToCharacterEntity(character: Character, pageNumber: Int): CharacterEntity {
        return CharacterEntity(
            uid = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            characterOriginName = character.origin.name,
            characterOriginUrl = character.origin.url,
            characterLocationName = character.location.name,
            characterLocationsUrl = character.location.url,
            imageUrl = character.imageUrl,
            characterUrl = character.characterUrl,
            created = character.created,
            pageNumber = pageNumber,
            episodesUrlListJson = Gson().toJson(character.episodesUrlList)
        )
    }

    private fun mapCharacterEntityToCharacter(characterEntity: CharacterEntity): Character {
        val gson = Gson()
        val itemType = object : TypeToken<List<String>>() {}.type
        val episodesUrlList = gson.fromJson<List<String>>(characterEntity.episodesUrlListJson, itemType)
        return Character(
            id = characterEntity.uid,
            name = characterEntity.name,
            status = characterEntity.status,
            species = characterEntity.species,
            type = characterEntity.type,
            gender = characterEntity.gender,
            origin = CharacterOrigin(characterEntity.characterOriginName, characterEntity.characterOriginUrl),
            location = CharacterLocation(characterEntity.characterLocationName, characterEntity.characterLocationsUrl),
            imageUrl = characterEntity.imageUrl,
            characterUrl = characterEntity.characterUrl,
            created = characterEntity.created,
            episodesUrlList = episodesUrlList
        )
    }

    override fun onCleared() {
        super.onCleared()
        resourceWrapper.savePageIntoSharedPreferences(CHARACTERS_PAGE_KEY, currentPage)
    }

    companion object {
        const val CHARACTERS_PAGE_KEY = "charactersPage"
    }
}