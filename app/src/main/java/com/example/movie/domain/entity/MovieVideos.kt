package com.example.movie.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieVideos(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("results")
    @Expose
    val list: List<ResultVideos>
)
