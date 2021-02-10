package com.mastomas.comicbookbrowser.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.repository.MarvelCharacterRepository

class CharacterViewModel(private val characterRepository: MarvelCharacterRepository) : ViewModel() {

    /**
     * Get and observe the latest modified characters from the Marvel Api
     */
    val latestHeroes: LiveData<PagingData<MarvelCharacter>> =
        characterRepository.getLatestCharacters().cachedIn(viewModelScope)

    private val currentQuery = MutableLiveData(EMPTY_SEARCH)
    val characters = currentQuery.switchMap {
        characterRepository.searchForCharacter(it).cachedIn(viewModelScope)
    }


    /**
     * Function to search for characters in the Marvel Api, make sure to be already observing [characters]
     */
    fun searchCharacters(search: String) {
        currentQuery.value = search
    }

    companion object {
        private const val EMPTY_SEARCH = "1"
    }
}