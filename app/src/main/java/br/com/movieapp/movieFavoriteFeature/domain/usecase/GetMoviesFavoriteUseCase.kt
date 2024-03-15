package br.com.movieapp.movieFavoriteFeature.domain.usecase

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.movieFavoriteFeature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMoviesFavoriteUseCase {
    suspend operator fun invoke(): Flow<List<Movie>>
}

class GetMoviesFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : GetMoviesFavoriteUseCase {
    override suspend fun invoke(): Flow<List<Movie>> =
        movieFavoriteRepository.getMovies()
}