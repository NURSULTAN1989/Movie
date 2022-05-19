package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.MovieRepositoryImpl
import com.example.movie.model.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application){
    private val repository = MovieRepositoryImpl(application)

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
            _movie.value = repository.getMovieBiId(movieId)
        }
    }
    fun composeFavorite(session: String, id: Int){
        viewModelScope.launch {
            _compose.value = repository.composeFavorite(session, id)
        }

    }
    fun addFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            _addFavoriteState.value = repository.addFavorite(movieId, sessionId)
        }
    }
    fun deleteFavorites(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            _addFavoriteState.value = repository.deleteFavorites(movieId, sessionId)
        }
    }
}