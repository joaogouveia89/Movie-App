package br.com.movieapp.core.doman.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails

class MovieDetailsFactory {
    fun create(poster: Poster) = when(poster){
        Poster.MovieDetails1 ->{
            MovieDetails(
                id = 1,
                title = "Name 1",
                voteAverage = 7.1,
                genres = listOf("Genre1", "Genre2", "Genre3"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "url1",
                releaseDate = "01/01/2001",
                duration = 145,
                voteCount = 7
            )
        }

        Poster.MovieDetails2 ->{
            MovieDetails(
                id = 2,
                title = "Name 2",
                voteAverage = 7.1,
                genres = listOf("Genre1", "Genre3", "Genre4"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "url2",
                releaseDate = "02/02/2002",
                duration = 149,
                voteCount = 2
            )
        }
    }

    sealed class Poster{
        object MovieDetails1: Poster()
        object MovieDetails2: Poster()
    }
}