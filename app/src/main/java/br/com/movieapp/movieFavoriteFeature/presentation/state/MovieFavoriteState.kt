package br.com.movieapp.movieFavoriteFeature.presentation.state

import br.com.movieapp.core.domain.model.Movie

data class MovieFavoriteState(
    val movies: List<Movie> = emptyList()
)