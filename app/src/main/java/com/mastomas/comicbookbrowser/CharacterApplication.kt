package com.mastomas.comicbookbrowser

import android.app.Application
import com.mastomas.comicbookbrowser.koin.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class CharacterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //Spinning up Koin for dependency injection
        startKoin {
            androidContext(this@CharacterApplication)
            modules(mainModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}