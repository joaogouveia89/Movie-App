package br.com.movieapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.movieapp.core.data.local.MovieDatabase
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

    @After
    fun tearDown(){
        database.close()
    }
}