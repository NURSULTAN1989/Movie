package com.example.movie.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.model.Common
import com.example.movie.model.Movie
import com.example.myfilms.data.models.PostMovie
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(context: Context):ViewModel(), CoroutineScope {
    private val movieDao: MovieDao
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _addFavoriteState = MutableLiveData<Boolean>()
    val addFavoriteState: LiveData<Boolean>
        get() = _addFavoriteState
    private val _compose = MutableLiveData<Boolean>()
    val compose: LiveData<Boolean>
        get() = _compose

    init {
        movieDao= MovieDatabase.getDatabase(context).postDao()
    }

    fun getMovieById(movieId: Int) {
        launch {
            val response= Common.getPostApi().getById(movieId)
            if (response.isSuccessful){
                _movie.value=response.body()
            }
            else{
                val movieFL = withContext(Dispatchers.IO) {
                    val result=movieDao.getMovieBiId(movieId)
                    result
                }
                _movie.value=movieFL
            }
        }

    }
    fun composeFavorite(session: String, id: Int){
        viewModelScope.launch {
            val response = Common.getInstance().getFavoriteMovie(session_id = session, id=id)
            if (response.isSuccessful) {
                if (response.body()?.favorite==true){
                    _compose.value = true
                }else{
                    _compose.value=false
                }
            }


        }

    }
    fun addFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val postMovie = PostMovie(media_id = movieId, favorite = true)
            val response = Common.getPostApi().addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
            if (response.isSuccessful) {
                _addFavoriteState.value = true
            } else {
                _addFavoriteState.value = false
            }
        }
    }
    fun deleteFavorites(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val postMovie = PostMovie(media_id = movieId, favorite = false)
            val response = Common.getPostApi().addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
            if (response.isSuccessful) {
                _addFavoriteState.value = true
            } else {
                _addFavoriteState.value = false
            }
        }
    }
}