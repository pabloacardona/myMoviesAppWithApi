package com.example.moviesappwithapi.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesappwithapi.data.model.Movie
import com.example.moviesappwithapi.data.remote.RetrofitClient
import com.example.moviesappwithapi.data.repository.MovieRepository
import com.example.moviesappwithapi.data.util.toUserFriendlyMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// estados de la pantalla de detalle
sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(val movie: Movie) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}

// lógica del detalle: datos TMDB y favoritos en Room
class MovieDetailViewModel(
    private val movieId: Int,
    private val repository: MovieRepository = MovieRepository(RetrofitClient.apiService)
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    // true si la película es favorita
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        loadMovieDetails()
        observeFavoriteStatus()
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            repository.isFavorite(movieId).collect { favorite ->
                _isFavorite.value = favorite
            }
        }
    }

    // alterna favorito en Room
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                repository.removeFavorite(movie.id)
            } else {
                repository.addFavorite(movie)
            }
        }
    }

    // carga el detalle y actualiza el estado
    fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            try {
                val movie = repository.getMovieDetails(movieId)
                _uiState.value = MovieDetailUiState.Success(movie = movie)
            } catch (e: Exception) {
                _uiState.value = MovieDetailUiState.Error(
                    message = e.toUserFriendlyMessage()
                )
            }
        }
    }

    // reintenta la carga
    fun retry() {
        loadMovieDetails()
    }
}

// factory de MovieDetailViewModel
class MovieDetailViewModelFactory(
    private val movieId: Int,
    private val repository: MovieRepository = MovieRepository(RetrofitClient.apiService)
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieId = movieId, repository = repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
    }
}
