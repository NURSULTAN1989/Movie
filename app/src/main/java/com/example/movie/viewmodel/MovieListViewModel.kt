package com.example.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Common
import com.example.movie.model.Event
import com.example.movie.model.Movie
import com.example.movie.model.MovieList
import com.example.movie.view.MyMovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieListViewModel: ViewModel(), CoroutineScope {


    lateinit var movie: MovieList
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<MovieList>()
    val liveData: LiveData<MovieList>
        get() = _liveData

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail


    init {
        getAllMovieListCoroutine()
    }

    private fun getAllMovieListCoroutine() {
        launch {
                val response=Common.getPostApi().getMoviesList()
            if (response.isSuccessful){
                _liveData.value=response.body()
                Log.d("CORUTINE_ERROR","Есть ДАННЫе")
            }else{
                Log.d("CORUTINE_ERROR","НЕТ ДАННЫХ")
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