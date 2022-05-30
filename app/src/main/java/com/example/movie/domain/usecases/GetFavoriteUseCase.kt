package com.example.movie.domain.usecases

import com.example.movie.domain.entity.Movie
import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class GetFavoriteUseCase(val repository: MovieRepository) {
    suspend fun getFavorite(session: String, page: Int): List<Movie>{
        return repository.getFavorite(session, page)
    }

//    override suspend fun run(session: String?, page: Int?): List<Movie> {
//        return repository.getFavorite(session!!, page!!)
//    }
}