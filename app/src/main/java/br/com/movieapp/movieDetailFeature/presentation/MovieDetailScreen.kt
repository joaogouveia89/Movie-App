package br.com.movieapp.movieDetailFeature.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.movieDetailFeature.presentation.components.MovieDetailContent
import br.com.movieapp.movieDetailFeature.presentation.state.MovieDetailState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    uiState: MovieDetailState,
    onAddFavorite: (Movie) -> Unit
) {
    val pagingMoviesSimilar = uiState.results.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppBar(
                title = R.string.detail_movie
            )
        },
        content = {
            MovieDetailContent(
                movieDetails = uiState.movieDetails,
                pagingMoviesSimilar = pagingMoviesSimilar,
                isLoading = uiState.isLoading,
                isError = uiState.error,
                iconColor = uiState.iconColor,
                onAddFavorite = { movie -> onAddFavorite(movie) }
            )
        }
    )
}