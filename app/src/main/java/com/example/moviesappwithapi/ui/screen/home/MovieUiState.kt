package com.example.moviesappwithapi.ui.screen.home

import com.example.moviesappwithapi.data.model.Movie

// estados exclusivos de la pantalla de películas
sealed interface MovieUiState {

    // cargando
    data object Loading : MovieUiState

    // carga exitosa
    data class Success(
        val movies: List<Movie>
    ) : MovieUiState

    // error con mensaje
    data class Error(
        val message: String
    ) : MovieUiState
}
