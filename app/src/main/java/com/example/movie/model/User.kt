package com.example.movie.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User( @SerializedName("id")
                 val id: Int,
                 @SerializedName("name")
                 val name: String,
                 @SerializedName("username")
                 val  username: String
):Parcelable