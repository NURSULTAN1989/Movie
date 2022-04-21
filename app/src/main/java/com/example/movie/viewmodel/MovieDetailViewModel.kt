package com.example.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Common
import com.example.movie.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel:ViewModel(), CoroutineScope {
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    fun getMovieById(movieId: Int) {
        launch {
            val response= Common.getPostApi().getById(movieId)
            if (response.isSuccessful){
                _movie.value=response.body()
            }

        }

    }
}