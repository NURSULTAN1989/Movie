package com.example.movie.domain.usecases

import com.example.movie.data.UserDB
import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class UpdateUserUseCase(val repository: MovieRepository): BaseUseCase<UserDB, String, Int>()  {
//    suspend fun updateUser(id: Int, uri: String): UserDB{
//        return repository.updateUser(id, uri)
//    }

    override suspend fun run(uri: String?, id: Int?): UserDB {
        return repository.updateUser(id!!, uri!!)
    }
}