package br.com.movieapp.movieFavoriteFeature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.movieFavoriteFeature.presentation.components.MovieFavoritesContent
import br.com.movieapp.movieFavoriteFeature.presentation.state.MovieFavoriteState

@Composable
fun MovieFavoritesScreen(
    uiState: MovieFavoriteState,
    navigateToDetailMovie: (Int) -> Unit
) {
    val movies = uiState.movies

    Scaffold(
        topBar = {
            MovieAppBar(
                title = R.string.favorite_movies
            )
        },
        content = { paddingValues ->
            MovieFavoritesContent(
                paddingValues = paddingValues,
                movies = movies,
                onClick = { movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}

@Preview
@Composable
fun MovieFavoritesScreenPreview() {
    MovieFavoritesScreen(
        uiState = MovieFavoriteState(),
        navigateToDetailMovie = {}
    )
}