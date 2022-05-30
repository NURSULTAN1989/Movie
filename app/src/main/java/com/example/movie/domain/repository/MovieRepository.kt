package com.example.movie.domain.repository

import com.example.movie.data.UserDB
import com.example.movie.domain.entity.LoginApprove
import com.example.movie.domain.entity.Movie

interface MovieRepository {
    suspend fun getAllMoviesList(): List<Movie>
    suspend fun getMovieBiId(movieId: Int): Movie
    suspend fun composeFavorite(session: String, id: Int): Boolean
    suspend fun addFavorite(movieId: Int, sessionId: String): Boolean
    suspend fun deleteFavorites(movieId: Int, sessionId: String): Boolean
    suspend fun getFavorite(session: String, page: Int): List<Movie>
    suspend fun login(data: LoginApprove): String
    suspend fun getUser(session: String): UserDB
    suspend fun updateUser(id: Int, uri: String): UserDB
    suspend fun deleteSession(session: String)
}