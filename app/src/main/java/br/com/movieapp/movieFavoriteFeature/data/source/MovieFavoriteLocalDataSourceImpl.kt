package br.com.movieapp.movieFavoriteFeature.data.source

import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.movieFavoriteFeature.data.mapper.toMovieEntity
import br.com.movieapp.movieFavoriteFeature.data.mapper.toMovies
import br.com.movieapp.movieFavoriteFeature.domain.source.MovieFavoriteLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieFavoriteLocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao
) : MovieFavoriteLocalDataSource {
    override fun getMovies(): Flow<List<Movie>> =
        dao.getMovies().map { it.toMovies() }

    override suspend fun insert(movie: Movie) {
        dao.insertMovie(movie.toMovieEntity())
    }

    override suspend fun delete(movie: Movie) {
        dao.deleteMovie(movie.toMovieEntity())
    }

    override suspend fun isFavorite(movieId: Int): Boolean =
        dao.isFavorite(movieId) != null
}