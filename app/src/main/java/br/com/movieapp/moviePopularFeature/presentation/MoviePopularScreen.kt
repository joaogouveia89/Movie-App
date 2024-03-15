package br.com.movieapp.moviePopularFeature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.moviePopularFeature.presentation.components.MovieContent
import br.com.movieapp.moviePopularFeature.presentation.state.MoviePopularState

@Composable
fun MoviePopularScreen(
    uiState: MoviePopularState,
    navigateToDetailMovie: (Int) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppBar(
                title = R.string.popular_movies
            )
        }
    ) { paddingValues ->
        MovieContent(
            pagingMovies = movies,
            paddingValues = paddingValues,
            onClick = { movieId ->
                UtilFunctions.logInfo("MOVIE_ID", movieId.toString())
                navigateToDetailMovie(movieId)
            }
        )
    }
}