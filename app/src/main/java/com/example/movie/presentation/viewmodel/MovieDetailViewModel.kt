package com.example.movie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.entity.Movie
import com.example.movie.domain.usecases.AddFavoriteUseCase
import com.example.movie.domain.usecases.ComposeFavoriteUseCase
import com.example.movie.domain.usecases.DeleteFavoriteUseCase
import com.example.movie.domain.usecases.GetMovieByIdUseCase
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application,
                           val getMovie: GetMovieByIdUseCase,
                           val addFavorite: AddFavoriteUseCase,
                           val deleteFavorite: DeleteFavoriteUseCase,
                           val composeFavorite: ComposeFavoriteUseCase
                           ) : AndroidViewModel(application){
//    private val repository = MovieRepositoryImpl(application)

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _addFavoriteState = MutableLiveData<Boolean>()
    val addFavoriteState: LiveData<Boolean>
        get() = _addFavoriteState
    private val _compose = MutableLiveData<Boolean>()
    val compose: LiveData<Boolean>
        get() = _compose

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            _movie.value = getMovie.getMovieBiId(movieId)
        }
    }
    fun composeFavorite(session: String, id: Int){
        viewModelScope.launch {
            _compose.value = composeFavorite.composeFavorite(session, id)
        }

    }
    fun addFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            _addFavoriteState.value = addFavorite.addFavorite(movieId, sessionId)
        }
    }
    fun deleteFavorites(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            _addFavoriteState.value = deleteFavorite.deleteFavorites(movieId, sessionId)
        }
    }
}