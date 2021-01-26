package com.mastomas.comicbookbrowser.util

import android.net.Uri
import com.mastomas.comicbookbrowser.model.CharactersJson
import com.mastomas.comicbookbrowser.model.ComicJson
import com.mastomas.comicbookbrowser.model.MarvelCharacter
import com.mastomas.comicbookbrowser.model.MarvelComic

fun CharactersJson.toListOfMarvelCharacters(): List<MarvelCharacter> {
    val legal = attributionText.orEmpty()
    return data?.results?.mapNotNull { it?.toMarvelCharacter(legal) } ?: emptyList()
}

fun ComicJson.toListOfMarvelComics(): List<MarvelComic> {
    val legal = attributionText.orEmpty()
    return data?.results?.mapNotNull { it?.toMarvelComic(legal) } ?: emptyList()
}

private fun CharactersJson.Data.Result.toMarvelCharacter(legal: String): MarvelCharacter =
    MarvelCharacter(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        thumbnail = Uri.parse("${thumbnail?.path}.${thumbnail?.extension}"),
        legalText = legal
    )

private fun ComicJson.Data.Result.toMarvelComic(legal: String): MarvelComic =
    MarvelComic(
        id = id ?: -1,
        title = title.orEmpty(),
        thumbnail = Uri.parse("${thumbnail?.path}.${thumbnail?.extension}"),
        infoUrl = Uri.parse(urls?.get(0)?.url ?: "www.marvel.com"),
        legalText = legal
    )