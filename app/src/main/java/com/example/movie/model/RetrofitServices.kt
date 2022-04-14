package com.example.movie.model

import retrofit2.Response
import retrofit2.http.*


interface RetrofitServices {
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String = "5f1af9c636cebf0ee04637b0a2c9f343",
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1,
    ): Response<MovieList>

    @GET("authentication/token/new")
    suspend fun createToken(
        @Query("api_key") apiKey: String = "5f1af9c636cebf0ee04637b0a2c9f343",
    ): Response<TokenResponse>

    @POST("authentication/token/new")
    suspend fun validateWithLogin(
        @Query("api_key") apiKey: String = "5f1af9c636cebf0ee04637b0a2c9f343",
        @Body body: BodyRequest,
    ): Response<TokenResponse>
}