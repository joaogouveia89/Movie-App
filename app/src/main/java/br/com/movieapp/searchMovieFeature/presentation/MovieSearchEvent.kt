package br.com.movieapp.searchMovieFeature.presentation

sealed class MovieSearchEvent {
    data class EnteredQuery(val query: String): MovieSearchEvent()
}