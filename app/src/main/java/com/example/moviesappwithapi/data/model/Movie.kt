package com.example.moviesappwithapi.data.model

import com.google.gson.annotations.SerializedName

// película de TMDB; @SerializedName mapea snake_case a camelCase
data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("poster_path")
    val posterPath: String?
)
