package br.com.movieapp.movieFavoriteFeature.domain.usecase

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movieFavoriteFeature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface AddMovieFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<ResultData<Unit>>
    data class Params(val movie: Movie)
}

class AddMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
): AddMovieFavoriteUseCase{
    override suspend fun invoke(params: AddMovieFavoriteUseCase.Params): Flow<ResultData<Unit>> =
        flow {
            val insert: Unit = movieFavoriteRepository.insert(params.movie)
            emit(ResultData.Success(insert))
        }.flowOn(Dispatchers.IO)

}