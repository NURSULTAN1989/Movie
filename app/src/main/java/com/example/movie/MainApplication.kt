package com.example.movie

import android.app.Application
import com.example.movie.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule, daoModule, repositoryModule, viewModelModule, sharedPreferencesModule)
        }
    }

}