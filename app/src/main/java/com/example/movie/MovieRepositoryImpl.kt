package com.example.movie

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.movie.model.*
import com.example.myfilms.data.models.LoginApprove
import com.example.myfilms.data.models.PostMovie
import com.example.myfilms.data.models.Session
import com.example.myfilms.data.models.Token
import com.example.retrofitexample.model.database.MovieDao
import com.example.retrofitexample.model.database.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieRepositoryImpl(application: Application) {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).postDao()
    private var movieList: List<Movie>? = null
    private var movie: Movie? = null
    private val apiService = Common.getPostApi()
    private val userDao = MovieDatabase.getDatabase(application).postDao()

    suspend fun getAllMoviesList(): List<Movie> {
            movieList = withContext(Dispatchers.IO) {
                try {
                    val response = apiService.getMoviesList()
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            movieDao.insertAll(result.results)
                        }
                        result?.results!!
                    } else {
                        movieDao.getAll()
                    }
                } catch (e: Exception) {
                    movieDao.getAll()
                }
            }
        return movieList ?: emptyList()
    }



    suspend fun getMovieBiId(movieId: Int): Movie {
            val response = apiService.getById(movieId)
            if (response.isSuccessful) {
                movie = response.body()!!
            } else {
                val movieFL = withContext(Dispatchers.IO) {
                    val result = movieDao.getMovieBiId(movieId)
                    result
                }
                movie = movieFL
            }
        return movie as Movie
    }
    suspend fun composeFavorite(session: String, id: Int): Boolean{
        var favorite =false
        val response = Common.getInstance().getFavoriteMovie(session_id = session, id=id)
        if (response.isSuccessful) {
            if (response.body()?.favorite==true){
               favorite = true
            }
        }
        return favorite
    }
   suspend fun addFavorite(movieId: Int, sessionId: String): Boolean {
       var favorite =false
        val postMovie = PostMovie(media_id = movieId, favorite = true)
        val response = apiService.addFavorite(
            session_id = sessionId,
            postMovie = postMovie
        )
        if (response.isSuccessful) {
            favorite = true
        }
       return favorite
    }
    suspend fun deleteFavorites(movieId: Int, sessionId: String): Boolean{
        var favorite =false
        val postMovie = PostMovie(media_id = movieId, favorite = false)
        val response = Common.getPostApi().addFavorite(
            session_id = sessionId,
            postMovie = postMovie
        )
        if (response.isSuccessful) {
            favorite = true
        }
        return favorite
    }

    suspend fun getFavorite(session: String, page: Int): List<Movie> {
            val response = apiService.getFavorites(session_id = session, page = page)
            if (response.isSuccessful) {
                movieList = response.body()?.results!!
            }
        return movieList ?: emptyList()
    }

    suspend fun login(data: LoginApprove): String {
        var session:String = ""
        val responseGet = apiService.getToken()
        if (responseGet.isSuccessful) {
            val loginApprove = LoginApprove(
                username = data.username,
                password = data.password,
                request_token = responseGet.body()?.request_token as String
            )
            val responseApprove = apiService.approveToken(loginApprove = loginApprove)
            if (responseApprove.isSuccessful) {
                val response =
                    apiService.createSession(token = responseApprove.body() as Token)
                if (response.isSuccessful) {
                    session = response.body()?.session_id as String
                }
            }
        }
        return session
    }

    suspend fun getUser(session: String): UserDB {
        var userDB:UserDB? = null
        withContext(Dispatchers.IO) {
                val response = apiService.getAccount(session_id = session)
                if (response.isSuccessful) {
                    val result = response.body() as User
                    val resultDB = userDao.getUser(result.id)
                    if (resultDB != null) {
                        userDB = resultDB
                    } else {
                        userDB = UserDB(result.id, result.name, result.username, "")
                        userDao.insertUser(userDB as UserDB)
                    }
                }
        }
        return userDB as UserDB
    }

    suspend fun updateUser(id: Int, uri: String): UserDB {
        var userDB:UserDB? = null
        withContext(Dispatchers.IO) {
            userDao.updateUser(id, uri)
            userDB = userDao.getUser(id)
        }
        return userDB as UserDB
    }

    suspend fun deleteSession(session: String) {
        apiService.deleteSession(sessionId = Session(session_id = session))
    }
}