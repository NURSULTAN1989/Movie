package com.example.movie.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Common
import com.example.movie.model.Event
import com.example.movie.model.Movie
import com.example.movie.model.MovieList
import com.example.movie.view.MyMovieAdapter
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieListViewModel(context: Context): ViewModel(), CoroutineScope {

    private val movieDao:MovieDao
    lateinit var movie: MovieList
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<List<Movie>>()
    val liveData: MutableLiveData<List<Movie>>
        get() = _liveData

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail


    init {
        getAllMovieListCoroutine()
        movieDao=MovieDatabase.getDatabase(context).postDao()
    }

    private fun getAllMovieListCoroutine() {
        launch {
            val list = withContext(Dispatchers.IO) {
                try {
                    val response =Common.getPostApi().getMoviesList()
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            movieDao.insertAll(result.results)
                        }
                        result?.results
                    } else {
                        movieDao.getAll()
                    }
                } catch (e: Exception) {
                    movieDao.getAll()
                }
            }
            list?.let {
                _liveData.value = it
            }

        }
    }
    val recyclerViewItemClickListener = object : MyMovieAdapter.MovieItemClick {
        override fun movieItemClick(item: Movie) {
            _openDetail.value = Event(item)
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}