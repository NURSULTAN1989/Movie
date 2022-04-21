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

    @POST("authentication/token/validate_with_login")
    suspend fun approveToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Body loginApprove: LoginApprove
    ): Response<Token>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body token: Token
    ): Response<Session>

    @Headers(
        "Accept: application/json;charset=utf-8",
        "Content-type: application/json;charset=utf-8"
    )
    @POST("account/{account_id}/favorite")
    suspend fun addFavorite(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Body postMovie: PostMovie
    ): Response<FavoriteResult>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Query("language") language: String = PARAMS_LANGUAGE,
        @Query("sort_by") sort_by: String = SORT_BY_POPULARITY,
        @Query("page") page: Int = PARAMS_PAGE
    ): Response<MovieList>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body sessionId: Session
    )

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = PARAMS_LANGUAGE
    ): Response<MovieVideos>

    companion object {

        private var SESSION_ID = ""
        private var API_KEY = "5f1af9c636cebf0ee04637b0a2c9f343"
        private var PARAMS_LANGUAGE = "ru"
        private var SORT_BY_POPULARITY = "popularity.desc"
        private var PARAMS_PAGE = 1
    }

}