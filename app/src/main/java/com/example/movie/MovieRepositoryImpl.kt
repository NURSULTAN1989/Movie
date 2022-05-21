package com.example.movie

import com.example.movie.model.Common
import com.example.movie.model.Movie
import com.example.movie.model.User
import com.example.movie.model.UserDB
import com.example.myfilms.data.models.LoginApprove
import com.example.myfilms.data.models.PostMovie
import com.example.myfilms.data.models.Session
import com.example.myfilms.data.models.Token
import com.example.retrofitexample.model.database.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieDao: MovieDao,
    private val api:Common
) {
//    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).postDao()
    private var movieList: List<Movie>? = null
    private var movie: Movie? = null
 //   private val apiService = Common.getPostApi()
 //   private val userDao = MovieDatabase.getDatabase(application).postDao()

    suspend fun getAllMoviesList(): List<Movie> {
            movieList = withContext(Dispatchers.IO) {
                try {
                    val response = api.getPostApi().getMoviesList()
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
            val response = api.getPostApi().getById(movieId)
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
        val response = api.getPostApi().addFavorite(
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
            val response = api.getPostApi().getFavorites(session_id = session, page = page)
            if (response.isSuccessful) {
                movieList = response.body()?.results!!
            }
        return movieList ?: emptyList()
    }

    suspend fun login(data: LoginApprove): String {
        var session:String = ""
        val responseGet = api.getPostApi().getToken()
        if (responseGet.isSuccessful) {
            val loginApprove = LoginApprove(
                username = data.username,
                password = data.password,
                request_token = responseGet.body()?.request_token as String
            )
            val responseApprove = api.getPostApi().approveToken(loginApprove = loginApprove)
            if (responseApprove.isSuccessful) {
                val response =
                    api.getPostApi().createSession(token = responseApprove.body() as Token)
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
                val response = api.getPostApi().getAccount(session_id = session)
                if (response.isSuccessful) {
                    val result = response.body() as User
                    val resultDB = movieDao.getUser(result.id)
                    if (resultDB != null) {
                        userDB = resultDB
                    } else {
                        userDB = UserDB(result.id, result.name, result.username, "")
                        movieDao.insertUser(userDB as UserDB)
                    }
                }
        }
        return userDB as UserDB
    }

    suspend fun updateUser(id: Int, uri: String): UserDB {
        var userDB:UserDB? = null
        withContext(Dispatchers.IO) {
            movieDao.updateUser(id, uri)
            userDB = movieDao.getUser(id)
        }
        return userDB as UserDB
    }

    suspend fun deleteSession(session: String) {
        api.getPostApi().deleteSession(sessionId = Session(session_id = session))
    }
}