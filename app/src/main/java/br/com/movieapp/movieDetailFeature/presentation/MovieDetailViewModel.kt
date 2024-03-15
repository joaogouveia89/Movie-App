package br.com.movieapp.movieDetailFeature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.util.Constants.MOVIE_DETAIL_ARGUMENT_KEY
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.movieDetailFeature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movieDetailFeature.presentation.state.MovieDetailState
import br.com.movieapp.movieFavoriteFeature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.IsMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addMovieFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(MovieDetailState())
        private set

    private val movieId = savedStateHandle.get<Int>(key = MOVIE_DETAIL_ARGUMENT_KEY)

    init {
        movieId?.let { safeMovieId ->
            checkedFavorite(MovieDetailEvent.CheckedFavorite(safeMovieId))
            getMovieDetail(MovieDetailEvent.GetMovieDetail(safeMovieId))
        }
    }

    private fun checkedFavorite(checkedFavorite: MovieDetailEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    fun onAddFavorite(movie: Movie) {
        if (uiState.iconColor == Color.White) {
            event(MovieDetailEvent.AddFavorite(movie = movie))
        } else {
            event(MovieDetailEvent.RemoveFavorite(movie = movie))
        }
    }

    private fun getMovieDetail(getMovieDetail: MovieDetailEvent.GetMovieDetail) {
        event(getMovieDetail)
    }

    private fun event(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.AddFavorite -> {
                viewModelScope.launch {
                    addMovieFavoriteUseCase.invoke(
                        params = AddMovieFavoriteUseCase.Params(event.movie)
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    iconColor = Color.Red
                                )
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "DETAIL",
                                    "Erro ao cadastrar filme nos favoritos"
                                )
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.CheckedFavorite -> {
                viewModelScope.launch {
                    isMovieFavoriteUseCase.invoke(
                        params = IsMovieFavoriteUseCase.Params(movieId = event.movieId)
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    iconColor = if (result.data) Color.Red else Color.White
                                )
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "DETAIL",
                                    "Erro ao atualizar filme nos favoritos"
                                )
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    deleteMovieFavoriteUseCase.invoke(
                        params = DeleteMovieFavoriteUseCase.Params(event.movie)
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    iconColor = Color.White
                                )
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "DETAIL",
                                    "Erro ao remover filme nos favoritos"
                                )
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.GetMovieDetail -> {
                viewModelScope.launch {
                    getMovieDetailsUseCase.invoke(
                        params = GetMovieDetailsUseCase.Params(
                            movieId = event.movieId
                        )
                    ).collect { resultData ->
                        when (resultData) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    movieDetails = resultData.data.second,
                                    results = resultData.data.first
                                )
                            }

                            is ResultData.Failure -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    error = resultData.e.message.toString()
                                )
                                UtilFunctions.logError(
                                    "DETAIL-ERROR",
                                    resultData.e.message.toString()
                                )
                            }

                            is ResultData.Loading -> {
                                uiState = uiState.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}