package com.mastomas.comicbookbrowser.koin

import com.mastomas.comicbookbrowser.repository.MarvelCharacterRepository
import com.mastomas.comicbookbrowser.repository.MarvelComicRepository
import com.mastomas.comicbookbrowser.viewmodel.CharacterDetailViewModel
import com.mastomas.comicbookbrowser.viewmodel.CharacterViewModel
import com.mastomas.comicbookbrowser.webservice.MarvelApiWebService
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalSerializationApi
val mainModule = module {
    factory { MarvelApiWebService.create() }
    single { MarvelCharacterRepository(get()) }
    single { MarvelComicRepository(get()) }
    viewModel { CharacterViewModel(get()) }
    viewModel { CharacterDetailViewModel(get()) }
}