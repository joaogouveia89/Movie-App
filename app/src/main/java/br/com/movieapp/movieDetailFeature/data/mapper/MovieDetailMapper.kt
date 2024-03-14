package br.com.movieapp.movieDetailFeature.data.mapper

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails

fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    imageUrl = backdropPathUrl.toString(),
    voteAverage = voteAverage,
)