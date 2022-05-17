package com.example.movie

import android.app.Application
import com.example.movie.model.Common
import com.example.movie.model.Movie
import com.example.movie.model.MovieList
import com.example.myfilms.data.models.LoginApprove
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieRepositoryImpl(application: Application):MovieRepository, CoroutineScope {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).postDao()
    lateinit var movie: List<Movie>
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    override fun getAllMoviesList(): List<Movie> {
        launch {
            movie = withContext(Dispatchers.IO) {
                try {
                    val response = Common.getPostApi().getMoviesList()
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
        }
        return movie
    }

    override fun getMovieBiId() {
        TODO("Not yet implemented")
    }

    override fun getFavorite() {
        TODO("Not yet implemented")
    }

    override fun login(data: LoginApprove) {
        TODO("Not yet implemented")
    }

    override fun getUser(session: String) {
        TODO("Not yet implemented")
    }

    override fun updateUser(id: Int, uri: String) {
        TODO("Not yet implemented")
    }

    override fun deleteSession(session: String) {
        TODO("Not yet implemented")
    }
}