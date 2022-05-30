package com.example.movie.domain.usecases

import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class AddFavoriteUseCase(val repository: MovieRepository): BaseUseCase<Boolean, Int, String>()  {
//    suspend fun addFavorite(movieId: Int, sessionId: String):Boolean{
//        return repository.addFavorite(movieId, sessionId)
//    }

    override suspend fun run(movieId: Int?, sessionId: String?): Boolean {
        return repository.addFavorite(movieId!!, sessionId!!)
    }
}