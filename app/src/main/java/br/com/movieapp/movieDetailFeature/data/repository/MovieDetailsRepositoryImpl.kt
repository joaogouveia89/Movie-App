package br.com.movieapp.movieDetailFeature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.movieDetailFeature.domain.repository.MovieDetailsRepository
import br.com.movieapp.movieDetailFeature.domain.source.MovieDetailRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailRemoteDataSource
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails =
        remoteDataSource.getMovieDetails(movieId)

    override suspend fun getMoviesSimilar(
        movieId: Int,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Movie>> =
        Pager(
            config = pagingConfig,
            pagingSourceFactory = { remoteDataSource.getSimilarMoviesPagingSource(movieId = movieId)}
        ).flow
}