package com.example.moviesappwithapi.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// singleton de Retrofit para TmdbApiService
object RetrofitClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val apiService: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }
}
