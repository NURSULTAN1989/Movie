package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    val success:Boolean,
    val expires_at:String,
    val request_token:String
)
