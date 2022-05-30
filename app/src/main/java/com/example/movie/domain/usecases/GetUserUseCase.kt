package com.example.movie.domain.usecases

import com.example.movie.data.UserDB
import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class GetUserUseCase(val repository: MovieRepository){
    suspend fun getUser(session: String): UserDB{
        return repository.getUser(session)
    }

//    override suspend fun run(session: String?, params2: Any?): UserDB {
//        return repository.getUser(session!!)
//    }
}