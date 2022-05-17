package com.example.movie

import com.example.movie.model.Movie
import com.example.movie.model.MovieList
import com.example.myfilms.data.models.LoginApprove

interface MovieRepository {
    fun getAllMoviesList():List<Movie>
    fun getMovieBiId()
    fun getFavorite()
    fun login(data: LoginApprove)
    fun getUser(session: String)
    fun updateUser(id: Int, uri: String)
    fun deleteSession(session: String)
}