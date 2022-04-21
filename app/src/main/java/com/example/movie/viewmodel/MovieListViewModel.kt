package com.example.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Common
import com.example.movie.model.Movie
import com.example.movie.model.MovieList
import com.example.movie.view.MyMovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieListViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<MovieList>()
    val liveData: LiveData<MovieList>
        get() = _liveData

    private val _openDetail = MutableLiveData<Movie>()
    val openDetail: LiveData<Movie>
        get() = _openDetail

    init {
        getAllMovieListCoroutine()
    }

    val movieClick = object : MyMovieAdapter.MovieItemClick {
        override fun movieItemClick(item: Movie) {
            _openDetail.value = item
        }
    }

    private fun getAllMovieListCoroutine() {

        launch {
            val response = Common.getPostApi().getMoviesList()
            if (response.isSuccessful) {
                _liveData.value = response.body()
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}