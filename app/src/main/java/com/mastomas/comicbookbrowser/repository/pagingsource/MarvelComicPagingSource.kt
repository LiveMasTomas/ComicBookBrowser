package com.mastomas.comicbookbrowser.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mastomas.comicbookbrowser.model.MarvelComic
import com.mastomas.comicbookbrowser.util.ApiUtils
import com.mastomas.comicbookbrowser.util.toListOfMarvelComics
import com.mastomas.comicbookbrowser.webservice.MarvelApiWebService
import timber.log.Timber
import java.util.Calendar

private const val MARVEL_API_START_INDEX = 0

class MarvelComicPagingSource(
    private val webService: MarvelApiWebService,
    private val characterId: String
) : PagingSource<Int, MarvelComic>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelComic> {
        val position = params.key ?: MARVEL_API_START_INDEX
        val timestamp = Calendar.getInstance().timeInMillis.toString()
        return try {
            val result = webService.getComics(
                characterId = characterId,
                limit = params.loadSize,
                offset = position,
                timestamp = timestamp,
                hash = ApiUtils.getMd5String(timestamp)
            )
            LoadResult.Page(
                data = result.toListOfMarvelComics(),
                prevKey = if (position == MARVEL_API_START_INDEX) null else position - params.loadSize,
                nextKey = if (result.data?.results.isNullOrEmpty()) null else position + params.loadSize
            )
        } catch (t: Throwable) {
            Timber.e(t, "Paging Source Failed")
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MarvelComic>): Int? =
        state.anchorPosition
}