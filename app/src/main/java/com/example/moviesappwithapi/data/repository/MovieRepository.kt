package com.example.moviesappwithapi.data.repository

import com.example.moviesappwithapi.data.local.FavoriteMovie
import com.example.moviesappwithapi.data.local.FavoriteMovieDao
import com.example.moviesappwithapi.data.model.Movie
import com.example.moviesappwithapi.data.remote.RetrofitClient
import com.example.moviesappwithapi.data.remote.TmdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// coordina datos remotos (TMDB) y locales (Room)
class MovieRepository(
    private val api: TmdbApiService = RetrofitClient.apiService,
    private val favoriteDao: FavoriteMovieDao? = null
) {

    // remoto: Retrofit / TMDB
    // películas populares
    suspend fun getPopularMovies(): List<Movie> {
        return api.getPopularMovies().results
    }

    // detalle por id
    suspend fun getMovieDetails(movieId: Int): Movie {
        return api.getMovieDetails(movieId = movieId)
    }

    // búsqueda por título
    suspend fun searchMovies(query: String): List<Movie> {
        return api.searchMovies(query = query).results
    }

    // local: Room / SQLite
    // favoritas como Flow
    fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return favoriteDao?.getAllFavorites() ?: flowOf(emptyList())
    }

    // true si la película es favorita
    fun isFavorite(movieId: Int): Flow<Boolean> {
        return favoriteDao?.isFavorite(movieId) ?: flowOf(false)
    }

    // guarda una favorita
    suspend fun addFavorite(movie: Movie) {
        favoriteDao?.insertFavorite(
            FavoriteMovie(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage
            )
        )
    }

    // elimina una favorita por id
    suspend fun removeFavorite(movieId: Int) {
        favoriteDao?.deleteFavoriteById(movieId)
    }
}
