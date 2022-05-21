package com.example.movie.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.movie.MovieRepositoryImpl
import com.example.movie.model.Common
import com.example.movie.view.LoginFragment
import com.example.movie.viewmodel.*
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val networkModule = module {
    single { getRetrofitService() }
}

val daoModule = module {
    single { getPostDao(context = get()) }
}

val repositoryModule = module {
    single { MovieRepositoryImpl(api = get(), movieDao = get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(repository = get() , application = get()) }
    viewModel { MovieDetailViewModel(repository = get() , application = get()) }
    viewModel { MovieListViewModel(repository = get() , application = get()) }
    viewModel { UserViewModel(repository = get() , application = get()) }
    viewModel { ViewModelFavorites(repository = get() , application = get()) }
}

val sharedPreferencesModule = module {
    single{
        getSharedPrefs(androidApplication())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences{
    return  androidApplication.getSharedPreferences(LoginFragment.APP_SETTINGS,  Context.MODE_PRIVATE)
}private fun getRetrofitService(): Common = Common
private fun getPostDao(context: Context): MovieDao = MovieDatabase.getDatabase(context).postDao()
