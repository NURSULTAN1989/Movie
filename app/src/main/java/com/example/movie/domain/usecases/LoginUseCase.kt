package com.example.movie.domain.usecases

import com.example.movie.domain.entity.LoginApprove
import com.example.movie.domain.repository.MovieRepository
import com.example.retrofitexample.domain.common.BaseUseCase

class LoginUseCase(val repository: MovieRepository): BaseUseCase<String, LoginApprove, Any?>()  {
//    suspend fun login(data: LoginApprove): String{
//        return repository.login(data)
//    }

    override suspend fun run(data: LoginApprove?, params2: Any?): String {
        return repository.login(data!!)
    }
}