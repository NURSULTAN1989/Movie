package com.example.retrofitexample.domain.common

import com.example.movie.domain.exception.ApiError


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}
