package com.example.moviesappwithapi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


// acceso a favoritos; Room genera la implementación

@Dao
interface FavoriteMovieDao {

    // todas las favoritas; Flow emite en cada cambio
    @Query("SELECT * FROM favorite_movies ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<FavoriteMovie>>


    // true si el id está en favoritos
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>


    // inserta o reemplaza una favorita
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)


    // elimina una favorita
    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
}
