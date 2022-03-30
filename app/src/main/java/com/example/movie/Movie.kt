package com.example.movie

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val name: String?,
    val realname: String?,
    val team: String?,
    val firstapperance: String?,
    val createdby: String?,
    val publisher: String?,
    val imageurl: String?,
    val bio: String?,
) : Parcelable
