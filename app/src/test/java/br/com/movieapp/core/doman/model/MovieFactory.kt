package br.com.movieapp.core.doman.model

import br.com.movieapp.core.domain.model.Movie

class MovieFactory {
    fun create(poster: Poster) = when(poster){
        Poster.Movie1 ->{
            Movie(
                id = 1,
                title = "Name 1",
                voteAverage = 7.1,
                imageUrl = "url1"
            )
        }

        Poster.Movie2 ->{
            Movie(
                id = 2,
                title = "Name 2",
                voteAverage = 7.9,
                imageUrl = "url2"
            )
        }
    }

    sealed class Poster{
        object Movie1: Poster()
        object Movie2: Poster()
    }
}