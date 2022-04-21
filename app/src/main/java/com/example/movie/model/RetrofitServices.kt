package com.example.movie.model

import com.example.myfilms.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface RetrofitServices {
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey:String = API_KEY,
        @Query("language") language:String = PARAMS_LANGUAGE,
        @Query("page") page:Int = 1
    ): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getById(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = PARAMS_LANGUAGE
    ): Response<Movie>
    @GET("authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<Token>

    companion object {

       // private var SESSION_ID = ""
        private var API_KEY = "5f1af9c636cebf0ee04637b0a2c9f343"
        private var PARAMS_LANGUAGE = "ru"
       // private var SORT_BY_POPULARITY = "popularity.desc"
        //private var PARAMS_PAGE = 1
    }

}