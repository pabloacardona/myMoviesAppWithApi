package com.example.moviesappwithapi.data.remote

import com.example.moviesappwithapi.BuildConfig
import com.example.moviesappwithapi.data.model.Movie
import com.example.moviesappwithapi.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// endpoints de TMDB; Retrofit genera la implementación
interface TmdbApiService {

    // películas populares: GET /3/movie/popular
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "es-ES"
    ): MovieResponse

    // detalle: GET /3/movie/{movie_id}
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "es-ES"
    ): Movie

    // búsqueda: GET /3/search/movie
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "es-ES"
    ): MovieResponse
}
