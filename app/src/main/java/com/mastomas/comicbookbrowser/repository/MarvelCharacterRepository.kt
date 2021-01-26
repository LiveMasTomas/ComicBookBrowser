package com.mastomas.comicbookbrowser.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.repository.pagingsource.MarvelCharacterPagingSource
import com.mastomas.comicbookbrowser.webservice.MarvelApiWebService

class MarvelCharacterRepository(private val webService: MarvelApiWebService) {

    fun getLatestCharacters(): LiveData<PagingData<MarvelCharacter>> =
        Pager(
            config = PagingConfig(
                pageSize = 25,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MarvelCharacterPagingSource(webService) }
        ).liveData

    fun searchForCharacter(query: String): LiveData<PagingData<MarvelCharacter>> =
        Pager(
            config = PagingConfig(
                pageSize = 25,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MarvelCharacterPagingSource(webService, query) }
        ).liveData
}