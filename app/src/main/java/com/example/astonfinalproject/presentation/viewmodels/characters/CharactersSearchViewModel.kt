package com.example.astonfinalproject.presentation.viewmodels.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersSearchViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
): ViewModel() {

    private val _characterList = MutableStateFlow<List<Character>>(listOf())
    val characterList: StateFlow<List<Character>> = _characterList

    fun filterCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            charactersRepository.filterCharacters(name, status, species, type, gender)
                .catch {

                }
                .collect {
                    val newCharactersList = _characterList.value.toMutableList().apply {
                        addAll(it.characters)
                    }
                    _characterList.value = newCharactersList
                }
        }
    }
}