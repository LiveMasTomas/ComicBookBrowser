package com.mastomas.comicbookbrowser.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mastomas.comicbookbrowser.model.MarvelComic
import com.mastomas.comicbookbrowser.repository.MarvelComicRepository

class CharacterDetailViewModel(private val repository: MarvelComicRepository) : ViewModel() {

    private val currentId = MutableLiveData(EMPTY_SEARCH)

    val comics: LiveData<PagingData<MarvelComic>> = currentId.switchMap {
        repository.getComicsForCharacter(it).cachedIn(viewModelScope)
    }

    fun getComicsForCharacter(characterId: String) {
        currentId.value = characterId
    }

    companion object {
        private const val EMPTY_SEARCH = "1"
    }
}