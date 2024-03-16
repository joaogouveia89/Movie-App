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

    private val movieEntityList = listOf(
        MovieEntity(movieId = 4, title = "Movie 4", imageUrl = "Url4"),
        MovieEntity(movieId = 1, title = "Movie 1", imageUrl = "Url1"),
        MovieEntity(movieId = 3, title = "Movie 3", imageUrl = "Url3"),
        MovieEntity(movieId = 2, title = "Movie 2", imageUrl = "Url2")
    )

    @Before
    fun setup(){
        hiltRule.inject()
        movieDao = database.movieDao()
    }

    @Test
    fun getMovies_should_return_list_of_movies_test() = runTest {
        // Given - Nothing

        // When
        val movies = movieDao.getMovies().first()

        // Then
        assertThat(movies.size).isEqualTo(0)
    }
    @Test
    fun getMovies_should_return_ordered_by_id_movie_list_test() = runTest {
        // Given
        addMultipleMovies(movieEntityList)

        val expectedIndexes = movieEntityList.map { it.movieId }.sorted()

        // When
        val movies = movieDao.getMovies().first()
        val resultIndexes = movies.map { it.movieId }


        // Then
        assertThat(movies.size).isEqualTo(movieEntityList.size)
        assertThat(resultIndexes).isEqualTo(expectedIndexes)
    }
    @Test
    fun insertMovie_should_insert_a_movie_successfully_test() = runTest{
        // Given
        val expectedMovie = movieEntityList.first()

        // When
        movieDao.insertMovie(expectedMovie)

        // Then
        val resultMovie = movieDao
            .getMovies()
            .first()
            .first()

        assertThat(resultMovie).isEqualTo(expectedMovie)
    }
    @Test
    fun isFavorite_should_return_a_favorite_movie_when_movie_is_marked_as_favorite_test() = runTest {
        // Given
        val favoriteMovie = movieEntityList
            .first()
            .copy(movieId = 5321)

        movieDao.insertMovie(favoriteMovie)

        // When
        val result = movieDao.isFavorite(favoriteMovie.movieId)

        // Then
        assertThat(result).isEqualTo(favoriteMovie)
    }
    @Test
    fun isFavorite_should_return_null_when_movie_is_not_marked_as_favorite_test() = runTest {
        // Given
        val favoriteMovie = movieEntityList
            .first()
            .copy(movieId = 5321)

        // When
        val result = movieDao.isFavorite(favoriteMovie.movieId)

        // Then
        assertThat(result).isNull()
    }
    @Test
    fun updateMovie_should_updated_a_movie_successfully() = runTest {
        // Given
        val movieEntity = movieEntityList.first()
        movieDao.insertMovie(movieEntity)
        val allMovies = movieDao.getMovies().first()
        val updateMovie = allMovies[0].copy(title = "Updated Movie")

        // When
        movieDao.insertMovie(updateMovie)

        // Then
        val movies = movieDao.getMovies().first()
        assertThat(movies[0]).isEqualTo(updateMovie)
    }
    @Test
    fun deleteMovie_should_delete_a_movie_successfully() = runTest {
        // Given
        val movieEntity = movieEntityList.first()
        movieDao.insertMovie(movieEntity)

        // When
        movieDao.deleteMovie(movieEntity)

        // Then
        val movies = movieDao.getMovies().first()
        assertThat(movies).isEmpty()
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