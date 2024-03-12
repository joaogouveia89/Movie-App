package br.com.movieapp.movieDetailFeature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.MovieDetailResponse
import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.core.util.toBackdropUrl
import br.com.movieapp.movieDetailFeature.domain.source.MovieDetailRemoteDataSource
import javax.inject.Inject

class MovieDetailRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
): MovieDetailRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails{
        val response = service.getMovie(movieId = movieId)
        val genres = response.genres.map { it.name }
        return MovieDetails(
            id = response.id,
            title = response.title,
            overview = response.overview,
            genres = genres,
            releaseDate = response.releaseDate,
            backdropPathUrl = response.backdropPath.toBackdropUrl(),
            voteAverage = response.voteAverage,
            duration = response.runtime,
            voteCount = response.voteCount
        )
    }


    override suspend fun getMoviesSimilar(page: Int, movieId: Int): MovieResponse =
        service.getMoviesSimilar(
            page = page,
            movieId = movieId
        )

    override fun getSimilarMoviesPagingSource(movieId: Int): MovieSimilarPagingSource =
        MovieSimilarPagingSource(
            remoteDataSource = this,
            movieId = movieId
        )
}