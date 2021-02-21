package com.mastomas.comicbookbrowser.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mastomas.comicbookbrowser.repository.pagingsource.MarvelComicPagingSource
import com.mastomas.comicbookbrowser.webservice.MarvelApiWebService

class MarvelComicRepository(private val webService: MarvelApiWebService) {

    fun getComicsForCharacter(characterId: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MarvelComicPagingSource(webService, characterId) }
        ).liveData
}