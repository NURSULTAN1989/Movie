package com.example.movie.domain.usecases

import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class DeleteFavoriteUseCase(val repository: MovieRepository) {
    suspend fun deleteFavorites(movieId: Int, sessionId: String): Boolean{
        return repository.deleteFavorites(movieId, sessionId)
    }

//    override suspend fun run(movieId: Int?, sessionId: String?): Boolean {
//        return repository.deleteFavorites(movieId!!, sessionId!!)
//    }
}