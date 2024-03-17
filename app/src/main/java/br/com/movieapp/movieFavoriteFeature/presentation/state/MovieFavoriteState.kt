package br.com.movieapp.movieFavoriteFeature.presentation.state

import br.com.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieFavoriteState(
    val movies: Flow<List<Movie>> = emptyFlow()
)