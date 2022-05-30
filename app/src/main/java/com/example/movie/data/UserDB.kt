package com.example.movie.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class UserDB( @PrimaryKey
                   @SerializedName("id")
                 val id: Int,
                 @SerializedName("name")
                 val name: String,
                 @SerializedName("username")
                 val  username: String,
                 @SerializedName("uri_img")
                 val uri_img: String
): Parcelable