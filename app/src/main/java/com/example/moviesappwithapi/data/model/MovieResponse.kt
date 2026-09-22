package com.example.moviesappwithapi.data.model

import com.google.gson.annotations.SerializedName

// respuesta de listados TMDB; solo se mapea results
data class MovieResponse(
    @SerializedName("results")
    val results: List<Movie>
)
