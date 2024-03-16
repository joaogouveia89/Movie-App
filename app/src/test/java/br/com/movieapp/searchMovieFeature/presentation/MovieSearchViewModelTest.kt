package br.com.movieapp.searchMovieFeature.presentation

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.doman.model.MovieFactory
import br.com.movieapp.core.doman.model.MovieSearchFactory
import br.com.movieapp.searchMovieFeature.domain.usecase.GetMovieSearchUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieSearchViewModelTest{
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMovieSearchUseCase: GetMovieSearchUseCase

    private val viewModel by lazy {
        MovieSearchViewModel(
            getMovieSearchUseCase = getMovieSearchUseCase
        )
    }

    private val fakePagingDataMovies = PagingData.from(
        listOf(
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.MovieSearch1),
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.MovieSearch2)
        )
    )

    @Test
    fun`must validate paging data object values when calling movie search paging data`() = runTest {
        // Given
        whenever(getMovieSearchUseCase(any())).thenReturn(
            flowOf(fakePagingDataMovies)
        )

        // When
        viewModel.fetch("")
        val result = viewModel.uiState.movies.first()

        assertThat(result).isNotNull()
    }

    @Test(expected = RuntimeException::class)
    fun`must throw an exception when the calling to the use case returns an exception`() = runTest{
        // Given
        whenever(getMovieSearchUseCase(any()))
            .thenThrow(RuntimeException())

        // When
        viewModel.fetch()
    }
}