package br.com.movieapp.movieDetailFeature.presentation

sealed class MovieDetailEvent {
    data class GetMovieDetail(val movieId: Int): MovieDetailEvent()
}