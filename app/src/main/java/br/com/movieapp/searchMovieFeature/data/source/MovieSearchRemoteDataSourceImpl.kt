package br.com.movieapp.searchMovieFeature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.SearchResponse
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.searchMovieFeature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
): MovieSearchRemoteDataSource {
    override fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource =
        MovieSearchPagingSource(
            query = query,
            remoteDataSource = this
        )

    override suspend fun getSearchMovies(page: Int, query: String): SearchResponse =
        service.searchMovie(page = page, query = query)
}