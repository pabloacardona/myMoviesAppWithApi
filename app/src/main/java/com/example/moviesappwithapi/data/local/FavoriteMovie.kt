package com.example.moviesappwithapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


// entidad Room: película favorita

// fila de la tabla favorite_movies
@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double
)
