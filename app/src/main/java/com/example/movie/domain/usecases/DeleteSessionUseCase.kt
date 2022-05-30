package com.example.movie.domain.usecases

import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class DeleteSessionUseCase(val repository: MovieRepository){
    suspend fun deleteSession(session: String){
        repository.deleteSession(session)
    }

//    override suspend fun run(params: Any?, session: String?) {
//        repository.deleteSession(session!!)
//    }
}