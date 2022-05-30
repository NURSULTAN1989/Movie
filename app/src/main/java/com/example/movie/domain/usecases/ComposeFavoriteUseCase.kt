package com.example.movie.domain.usecases

import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class ComposeFavoriteUseCase(val repository: MovieRepository) : BaseUseCase<Boolean, Int, String>()  {
//    suspend fun composeFavorite(session: String, id: Int): Boolean{
//        return repository.composeFavorite(session, id)
//    }

    override suspend fun run(id: Int?, session: String?): Boolean {
        return repository.composeFavorite(session!!, id!!)
    }
}