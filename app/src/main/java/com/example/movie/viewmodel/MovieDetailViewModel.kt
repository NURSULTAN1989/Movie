package com.example.movie.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Common
import com.example.movie.model.Movie
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(private val context: Context):ViewModel(), CoroutineScope {
    private val movieDao: MovieDao
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    init {
        movieDao= MovieDatabase.getDatabase(context).postDao()
    }

    fun getMovieById(movieId: Int) {
        launch {
            val movieFL = withContext(Dispatchers.IO) {
                val result=movieDao.getMovieBiId(movieId)
                result
            }
                _movie.value=movieFL

            /*val response= Common.getPostApi().getById(movieId)
            if (response.isSuccessful){
                _movie.value=response.body()
            }*/

        }

    }
}