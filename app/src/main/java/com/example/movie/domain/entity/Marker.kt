package com.example.movie.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class Marker(
    @PrimaryKey
    var id: Int,
    val lat: Double,
    val lng: Double,
    val title: String
)