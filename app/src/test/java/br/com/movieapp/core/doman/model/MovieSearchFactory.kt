package br.com.movieapp.core.doman.model

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieSearch

class MovieSearchFactory {
    fun create(poster: Poster) = when(poster){
        Poster.MovieSearch1 ->{
            MovieSearch(
                id = 1,
                voteAverage = 7.1,
                imageUrl = "url1"
            )
        }

        Poster.MovieSearch2 ->{
            MovieSearch(
                id = 2,
                voteAverage = 7.9,
                imageUrl = "url2"
            )
        }
    }

    sealed class Poster{
        object MovieSearch1: Poster()
        object MovieSearch2: Poster()
    }
}