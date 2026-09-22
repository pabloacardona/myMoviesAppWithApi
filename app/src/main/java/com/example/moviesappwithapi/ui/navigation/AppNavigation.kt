package com.example.moviesappwithapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesappwithapi.ui.screen.detail.MovieDetailScreen
import com.example.moviesappwithapi.ui.screen.favorites.FavoritesScreen
import com.example.moviesappwithapi.ui.screen.home.MovieListScreen

// rutas de navegación
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int): String = "detail/$movieId"
    }
}

// grafo de navegación (Navigation Compose)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // inicio: listado de películas
        composable(route = Screen.Home.route) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    // navega solo con movieId
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }

        // favoritas (Room)
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // detalle: recibe movieId
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
