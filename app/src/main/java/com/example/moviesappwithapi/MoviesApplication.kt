package com.example.moviesappwithapi

import android.app.Application
import com.example.moviesappwithapi.data.local.AppDatabase
import com.example.moviesappwithapi.data.remote.RetrofitClient
import com.example.moviesappwithapi.data.repository.MovieRepository


// aplicación: expone un MovieRepository compartido (TMDB + Room)
class MoviesApplication : Application() {


    // db local Room
    val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy {
        MovieRepository(
            api = RetrofitClient.apiService,
            favoriteDao = database.favoriteMovieDao()
        )
    }
}
