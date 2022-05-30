package com.example.movie.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.Event
import com.example.movie.domain.entity.Movie
import com.example.movie.domain.usecases.GetFavoriteUseCase
import com.example.movie.presentation.view.MyMovieAdapter
import kotlinx.coroutines.launch

class ViewModelFavorites(application: Application, private val getFavorite: GetFavoriteUseCase) : AndroidViewModel(application) {

//    private val repository = MovieRepositoryImpl(application)

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail

    fun getFavoriteMovie(session: String, page: Int) {
        viewModelScope.launch {
            _movies.value = getFavorite.getFavorite(session, page)
        }
    }
    val recyclerViewItemClickListener = object : MyMovieAdapter.MovieItemClick {
        override fun movieItemClick(item: Movie) {
            _openDetail.value = Event(item)
        }
    }
}