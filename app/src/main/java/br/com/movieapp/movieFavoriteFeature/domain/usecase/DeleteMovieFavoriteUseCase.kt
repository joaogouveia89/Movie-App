package br.com.movieapp.movieFavoriteFeature.domain.usecase

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movieFavoriteFeature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DeleteMovieFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<ResultData<Unit>>
    data class Params(val movie: Movie)
}

class DeleteMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
): DeleteMovieFavoriteUseCase{
    override suspend fun invoke(params: DeleteMovieFavoriteUseCase.Params): Flow<ResultData<Unit>> =
        flow {
            val delete: Unit = movieFavoriteRepository.delete(params.movie)
            emit(ResultData.Success(delete))
        }.flowOn(Dispatchers.IO)

}