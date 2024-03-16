package br.com.movieapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.movieapp.core.data.local.MovieDatabase
import br.com.movieapp.core.data.local.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@SmallTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Ensures that LiveData and other asynchronous tasks run synchronously in tests
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: MovieDatabase

    private lateinit var movieDao: MovieDao

    @Before
    fun setup(){
        hiltRule.inject()
        movieDao = database.movieDao()
    }


    @Test
    fun getMoviesTest() = runTest {
        // Given - Nothing

        // When
        val movies = movieDao.getMovies().first()

        // Then
        assertThat(movies.size).isEqualTo(0)
    }

    @Test
    fun getMovies_orderedByIdTest() = runTest {
        // Given
        val movieEntities = listOf(
            MovieEntity(movieId = 4, title = "Movie 4", imageUrl = "Url4"),
            MovieEntity(movieId = 1, title = "Movie 1", imageUrl = "Url1"),
            MovieEntity(movieId = 3, title = "Movie 3", imageUrl = "Url3"),
            MovieEntity(movieId = 2, title = "Movie 2", imageUrl = "Url2")
        )

        addMultipleMovies(movieEntities)

        val expectedIndexes = movieEntities.map { it.movieId }.sorted()

        // When
        val movies = movieDao.getMovies().first()
        val resultIndexes = movies.map { it.movieId }


        // Then
        assertThat(movies.size).isEqualTo(movieEntities.size)
        assertThat(resultIndexes).isEqualTo(expectedIndexes)
    }

    @After
    fun tearDown(){
        database.close()
    }

    private suspend fun addMultipleMovies(movies: List<MovieEntity>){
        for(movie in movies){
            movieDao.insertMovie(movie)
        }
    }
}