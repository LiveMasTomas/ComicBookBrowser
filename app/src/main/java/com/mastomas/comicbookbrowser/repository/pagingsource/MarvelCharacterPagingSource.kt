package com.mastomas.comicbookbrowser.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.util.ApiUtils
import com.mastomas.comicbookbrowser.util.toListOfMarvelCharacters
import com.mastomas.comicbookbrowser.webservice.MarvelApiWebService
import timber.log.Timber
import java.util.Calendar


private const val MARVEL_API_START_INDEX = 0

/**
 * Paging Source for [MarvelCharacter], can take a query for searching Marvel Api
 */
class MarvelCharacterPagingSource(
    private val webService: MarvelApiWebService,
    private val query: String? = null
) : PagingSource<Int, MarvelCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        val position = params.key ?: MARVEL_API_START_INDEX
        val timestamp = Calendar.getInstance().timeInMillis.toString()
        return try {
            val result = webService.getCharacters(
                name = query,
                offset = position,
                limit = params.loadSize,
                timestamp = timestamp,
                hash = ApiUtils.getMd5String(timestamp)
            )
            LoadResult.Page(
                data = result.toListOfMarvelCharacters(),
                prevKey = if (position == MARVEL_API_START_INDEX) null else position - params.loadSize,
                nextKey = if (result.data?.results.isNullOrEmpty()) null else position + params.loadSize
            )
        } catch (t: Throwable) {
            Timber.e(t, "Paging Source Failed")
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? =
        state.anchorPosition
}