package com.example.movie.model

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Common {

    private val LOCK = Any()
    private var apiService: RetrofitServices? = null
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun getInstance(): RetrofitServices {

        apiService?.let { return it }

        synchronized(LOCK) {

            apiService?.let { return it }

            val instance = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(getOkHttp())
                .build()
            retrofit = instance
            apiService = instance.create(RetrofitServices::class.java)
            return apiService as RetrofitServices
        }
    }

    fun getPostApi(): RetrofitServices {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
        return retrofit.create(RetrofitServices::class.java)
    }

    private fun getOkHttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
        return okHttpClient.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}