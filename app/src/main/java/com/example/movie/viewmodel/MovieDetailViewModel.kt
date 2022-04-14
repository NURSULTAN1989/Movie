package com.example.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel:ViewModel() {
    private val job = Job()



    private val _liveData = MutableLiveData<Movie>()
    val liveData: LiveData<Movie>
        get() = _liveData

    fun getMovie(movie: Movie) {
        _liveData.value=movie
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}