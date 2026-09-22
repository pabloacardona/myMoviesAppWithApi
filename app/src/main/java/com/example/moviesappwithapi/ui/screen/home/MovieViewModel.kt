package com.example.moviesappwithapi.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesappwithapi.data.remote.RetrofitClient
import com.example.moviesappwithapi.data.repository.MovieRepository
import com.example.moviesappwithapi.data.util.toUserFriendlyMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// estado y lógica del listado; expone StateFlow a Compose
class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    // estado interno mutable
    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)

    // estado público de solo lectura
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private var currentQuery: String = ""

    // carga películas populares
    fun loadMovies() {
        currentQuery = ""
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                val movies = repository.getPopularMovies()
                _uiState.value = MovieUiState.Success(movies = movies)
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(
                    message = e.toUserFriendlyMessage()
                )
            }
        }
    }

    // busca por título; vacío restaura populares
    fun searchMovies(query: String) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) {
            loadMovies()
            return
        }

        currentQuery = trimmed
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                val movies = repository.searchMovies(trimmed)
                _uiState.value = MovieUiState.Success(movies = movies)
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(
                    message = e.toUserFriendlyMessage()
                )
            }
        }
    }

    // reintenta la última operación
    fun retry() {
        if (currentQuery.isBlank()) {
            loadMovies()
        } else {
            searchMovies(currentQuery)
        }
    }
}

// factory de MovieViewModel con MovieRepository
class MovieViewModelFactory(
    private val repository: MovieRepository = MovieRepository(RetrofitClient.apiService)
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
    }
}
