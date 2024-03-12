package br.com.movieapp.searchMovieFeature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.searchMovieFeature.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieSearchUseCase {
    operator fun invoke(params: Params): Flow<PagingData<MovieSearch>>
    data class Params(val query: String)
}

class GetMovieSearchUseCaseImpl @Inject constructor(
    val repository: MovieSearchRepository
): GetMovieSearchUseCase{
    override fun invoke(params: GetMovieSearchUseCase.Params): Flow<PagingData<MovieSearch>> =
        repository.getSearchMovies(
            query = params.query,
            pagingConfig = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            )
        )
}