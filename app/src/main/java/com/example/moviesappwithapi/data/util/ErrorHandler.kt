package com.example.moviesappwithapi.data.util

import retrofit2.HttpException
import java.io.IOException














// convierte excepciones de red/HTTP en mensajes para el usuario
fun Throwable.toUserFriendlyMessage(): String {
    return when (this) {
        is IOException -> { // excepción de red
            "No pudimos conectar con el servidor. Verifica tu conexión a internet e intenta nuevamente."
        }
        is HttpException -> {
            when (code()) {
                401, 403 -> "Clave de API no válida o ausente. Verifica TMDB_API_KEY en local.properties."
                404 -> "La información solicitada no fue encontrada."
                in 500..599 -> "El servidor de TMDB tiene inconvenientes temporales. Intenta nuevamente más tarde."
                else -> "Error en la respuesta del servidor (${code()}). Intenta nuevamente."
            }
        }
        else -> {
            "No pudimos cargar la información. Intenta nuevamente."
        }
    }
}
