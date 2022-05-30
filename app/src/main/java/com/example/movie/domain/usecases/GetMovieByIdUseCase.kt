package com.example.movie.domain.usecases

import com.example.movie.domain.entity.Movie
import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class GetMovieByIdUseCase(val repository: MovieRepository): BaseUseCase<Movie, Any?, Int>() {
//    suspend fun getMovieBiId(movieId: Int): Movie{
//        return repository.getMovieBiId(movieId)
//    }

    override suspend fun run(params: Any?, movieId: Int?): Movie {
        return repository.getMovieBiId(movieId!!)
    }
}