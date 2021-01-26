package com.mastomas.comicbookbrowser.webservice

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mastomas.comicbookbrowser.BuildConfig
import com.mastomas.comicbookbrowser.model.CharactersJson
import com.mastomas.comicbookbrowser.model.ComicJson
import com.mastomas.comicbookbrowser.util.ApiUtils.MODIFIED
import com.mastomas.comicbookbrowser.util.ApiUtils.TITLE
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@OptIn(ExperimentalSerializationApi::class)
interface MarvelApiWebService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") name: String? = null,
        @Query("orderBy") order: String = MODIFIED,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("apikey") apikey: String = BuildConfig.MARVEL_PUBLIC_KEY,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): CharactersJson

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: String,
        @Query("orderBy") order: String = TITLE,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("apikey") apikey: String = BuildConfig.MARVEL_PUBLIC_KEY,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): ComicJson

    companion object {
        fun create(): MarvelApiWebService =
            Retrofit
                .Builder()
                .baseUrl(BuildConfig.MARVEL_BASE_URL)
                .addConverterFactory(
                    Json {
                        ignoreUnknownKeys = true
                    }.asConverterFactory("application/json".toMediaType())
                )
                .build()
                .create(MarvelApiWebService::class.java)
    }
}