package br.com.movieapp.movieFavoriteFeature.domain.usecase

import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movieFavoriteFeature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IsMovieFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<ResultData<Boolean>>
    data class Params(val movieId: Int)
}

class IsMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : IsMovieFavoriteUseCase {
    override suspend fun invoke(params: IsMovieFavoriteUseCase.Params): Flow<ResultData<Boolean>> =
        flow {
            val isFavorite: Boolean = movieFavoriteRepository.isFavorite(movieId = params.movieId)
            emit(ResultData.Success(isFavorite))
        }.flowOn(Dispatchers.IO)

}