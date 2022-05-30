package com.example.movie.domain.usecases

import com.example.movie.domain.entity.Movie
import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class GetAllMoviesListUseCase(val repository: MovieRepository): BaseUseCase<List<Movie>, Any?, Any?>() {
//    suspend fun getAllMoviesList(): List<Movie> {
//        return repository.getAllMoviesList()
//    }

    override suspend fun run(params: Any?, params2: Any?): List<Movie> {
        return repository.getAllMoviesList()
    }
}