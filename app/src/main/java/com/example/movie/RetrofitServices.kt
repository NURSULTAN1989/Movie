package com.example.movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

interface RetrofitServices {
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey:String = "5f1af9c636cebf0ee04637b0a2c9f343",
        @Query("language") language:String = "ru",
        @Query("page") page:Int = 1
    ): MovieList
}