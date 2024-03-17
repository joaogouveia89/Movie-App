package br.com.movieapp.movieFavoriteFeature.presentation

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.doman.model.MovieFactory
import br.com.movieapp.movieFavoriteFeature.domain.usecase.GetMoviesFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieFavoritesViewModelTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMoviesFavoriteUseCase: GetMoviesFavoriteUseCase

    private val viewModel by lazy {
        MovieFavoritesViewModel(
            getMoviesFavoriteUseCase = getMoviesFavoriteUseCase
        )
    }

    private val moviesFavorites = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Movie1),
        MovieFactory().create(poster = MovieFactory.Poster.Movie2)
    )


    @Test
    fun `must validate the data object values and calling list of favorites`() = runTest {
        // Given
        whenever(getMoviesFavoriteUseCase()).thenReturn(
            flowOf(moviesFavorites)
        )

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result).contains(moviesFavorites[0])
    }
}