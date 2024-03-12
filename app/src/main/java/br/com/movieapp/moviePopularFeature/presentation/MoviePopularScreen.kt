package br.com.movieapp.moviePopularFeature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.moviePopularFeature.presentation.components.MovieContent
import br.com.movieapp.moviePopularFeature.presentation.state.MoviePopularState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@Composable
fun MoviePopularScreen(
    uiState: MoviePopularState,
    navigateToDetailMovie: (Int) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.popular_movies),
                        color = white
                    )
                },
                backgroundColor = black
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