package com.example.movie.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.movie.data.MovieRepositoryImpl
import com.example.movie.data.network.Common
import com.example.movie.presentation.view.LoginFragment
import com.example.movie.presentation.viewmodel.*
import com.example.movie.data.database.MovieDao
import com.example.movie.data.database.MovieDatabase
import com.example.movie.domain.repository.MovieRepository
import com.example.movie.domain.usecases.*
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
    single<MovieRepository>{ MovieRepositoryImpl(api = get(), movieDao = get()) }
}
val useCaseModule = module {
    single { GetAllMoviesListUseCase(repository = get()) }
    single { AddFavoriteUseCase(repository = get()) }
    single { ComposeFavoriteUseCase(repository = get()) }
    single { DeleteFavoriteUseCase(repository = get()) }
    single { DeleteSessionUseCase(repository= get()) }
    single { GetFavoriteUseCase(repository = get()) }
    single { GetMovieByIdUseCase(repository = get()) }
    single { GetUserUseCase(repository = get()) }
    single { LoginUseCase(repository = get()) }
    single { UpdateUserUseCase(repository= get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(logUseCase = get() , application = get()) }
    viewModel { MovieDetailViewModel(
        getMovie = get() ,
        addFavorite = get() ,
        deleteFavorite = get() ,
        composeFavorite = get(),
        application = get()) }
    viewModel { MovieListViewModel(getMovieList = get() , application = get()) }
    viewModel { UserViewModel(
        getUser = get() ,
        updateUser = get() ,
        deleteSession = get() ,
        application = get()) }
    viewModel { ViewModelFavorites(getFavorite = get() , application = get()) }
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
}
val listMod = networkModule + daoModule + repositoryModule + viewModelModule + sharedPreferencesModule + useCaseModule
private fun getRetrofitService(): Common = Common
private fun getPostDao(context: Context): MovieDao = MovieDatabase.getDatabase(context).postDao()
