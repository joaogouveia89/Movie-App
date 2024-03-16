package br.com.movieapp.moviePopularFeature.presentation

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.doman.model.MovieFactory
import br.com.movieapp.moviePopularFeature.domain.usecase.GetPopularMoviesUseCase
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePopularViewModelTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private val viewModel by lazy {
        MoviePopularViewModel(
            getPopularMoviesUseCase = getPopularMoviesUseCase
        )
    }

    private val fakePagingDataMovies = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Movie1),
            MovieFactory().create(poster = MovieFactory.Poster.Movie2)
        )
    )

    @Test
    fun `must validate paging data object values when calling paging data from movies`() = runTest{
        // Given

        whenever(getPopularMoviesUseCase()).thenReturn(
            flowOf(fakePagingDataMovies)
        )

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNotNull()
    }
}