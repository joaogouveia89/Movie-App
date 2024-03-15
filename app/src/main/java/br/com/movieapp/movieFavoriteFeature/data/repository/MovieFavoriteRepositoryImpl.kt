package br.com.movieapp.movieFavoriteFeature.data.repository

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.movieFavoriteFeature.domain.repository.MovieFavoriteRepository
import br.com.movieapp.movieFavoriteFeature.domain.source.MovieFavoriteLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieFavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: MovieFavoriteLocalDataSource
) : MovieFavoriteRepository {
    override fun getMovies(): Flow<List<Movie>> =
        localDataSource.getMovies()

    override suspend fun insert(movie: Movie) {
        localDataSource.insert(movie)
    }

    override suspend fun delete(movie: Movie) {
        localDataSource.delete(movie)
    }

    override suspend fun isFavorite(movieId: Int): Boolean =
        localDataSource.isFavorite(movieId)
}