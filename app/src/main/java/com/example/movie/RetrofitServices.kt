package com.example.movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface RetrofitServices {
    @GET("marvel")
    fun getMovieList(): Call<List<Movie>>
    @GET("marvel/{name}")
    fun getMoviebyName(@Path("name") name: String):Call<Movie>
}