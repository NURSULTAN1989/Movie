package com.example.movie.model

import com.example.movie.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey:String = "5f1af9c636cebf0ee04637b0a2c9f343",
        @Query("language") language:String = "ru",
        @Query("page") page:Int = 1
    ): MovieList
}