package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.MovieRepositoryImpl
import com.example.movie.model.Event
import com.example.movie.model.Movie
import com.example.movie.view.MyMovieAdapter
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application) : AndroidViewModel(application){
    private val repository = MovieRepositoryImpl(application)
    private val movieDao:MovieDao


    private val _liveData = MutableLiveData<List<Movie>>()
    val liveData: MutableLiveData<List<Movie>>
        get() = _liveData

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail


    init {
        getAllMovieListCoroutine()
        movieDao=MovieDatabase.getDatabase(application).postDao()
    }

    private fun getAllMovieListCoroutine() {
        viewModelScope.launch {  _liveData.value = repository.getAllMoviesList() }
    }
    val recyclerViewItemClickListener = object : MyMovieAdapter.MovieItemClick {
        override fun movieItemClick(item: Movie) {
            _openDetail.value = Event(item)
        }
    }
}