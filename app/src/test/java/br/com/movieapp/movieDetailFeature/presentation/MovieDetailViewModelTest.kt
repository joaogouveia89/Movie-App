package br.com.movieapp.movieDetailFeature.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.doman.model.MovieDetailsFactory
import br.com.movieapp.core.doman.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movieDetailFeature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movieFavoriteFeature.domain.usecase.IsMovieFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var addMovieFavoriteUseCase: AddMovieFavoriteUseCase

    @Mock
    lateinit var deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase

    @Mock
    lateinit var isMovieFavoriteUseCase: IsMovieFavoriteUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private val movieDetailsFactory =
        MovieDetailsFactory().create(poster = MovieDetailsFactory.Poster.MovieDetails1)

    private val pagingData = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Movie1),
            MovieFactory().create(poster = MovieFactory.Poster.Movie2),
        )
    )

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Movie1)

    private val viewModel by lazy {
        MovieDetailViewModel(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            addMovieFavoriteUseCase = addMovieFavoriteUseCase,
            deleteMovieFavoriteUseCase = deleteMovieFavoriteUseCase,
            isMovieFavoriteUseCase = isMovieFavoriteUseCase,
            savedStateHandle = savedStateHandle.apply {
                whenever(savedStateHandle.get<Int>("movieId")).thenReturn(movie.id)
            }
        )
    }

    @Test
    fun `must notify uiState with Success when get movies similar and movie details returns success`() =
        runTest {
            // Given
            whenever(getMovieDetailsUseCase(any()))
                .thenReturn(
                    flowOf(
                        ResultData.Success(
                            flowOf(pagingData) to movieDetailsFactory
                        )
                    )
                )

            //
            val argumentCaptor = argumentCaptor<GetMovieDetailsUseCase.Params>()

            // Mocking the checkedFavorite function
            doNothing().whenever(viewModel).checkedFavorite(any())

            // When
            viewModel.uiState.isLoading

            // Then
            verify(getMovieDetailsUseCase(argumentCaptor.capture()))
            assertThat(movieDetailsFactory.id).isEqualTo(argumentCaptor.firstValue.movieId)
            val movieDetails = viewModel.uiState.movieDetails
            val results = viewModel.uiState.results

            assertThat(movieDetails).isNotNull()
            assertThat(results).isNotNull()
        }

}
