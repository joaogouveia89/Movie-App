package br.com.movieapp.moviePopularFeature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.paging.MoviePagingSource
import br.com.movieapp.moviePopularFeature.domain.source.MoviePopularRemoteDataSource

class MoviePopularRemoteDataSourceImpl constructor(
    private val service: MovieService
): MoviePopularRemoteDataSource {
    override fun getPopularMoviesPagingSource(): MoviePagingSource =
        MoviePagingSource(this)

    override suspend fun getPopularMovies(page: Int): MovieResponse =
        service.getPopularMovies(page = page)
}