package com.example.moviesappwithapi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

// url absoluta de imagen TMDB: base + size + path
fun buildTmdbImageUrl(posterPath: String?, size: String = "w500"): String? {
    return if (!posterPath.isNullOrBlank()) {
        "https://image.tmdb.org/t/p/$size$posterPath"
    } else {
        null
    }
}

// imagen con Coil; placeholder Material 3 si posterPath es nulo
@Composable
fun MoviePosterImage(
    posterPath: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: String = "w500",
    contentScale: ContentScale = ContentScale.Crop
) {
    val fullUrl = buildTmdbImageUrl(posterPath, size)

    if (fullUrl != null) {
        AsyncImage(
            model = fullUrl,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sin póster",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
