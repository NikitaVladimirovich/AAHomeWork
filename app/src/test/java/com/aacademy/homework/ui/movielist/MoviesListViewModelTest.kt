package com.aacademy.homework.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aacademy.homework.MainCoroutineScopeRule
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.foundations.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class MoviesListViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesListViewModel

    @MockK
    private lateinit var dataRepository: DataRepository

    @MockK
    lateinit var moviesObserverMockito: Observer<Resource<List<Movie>>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { moviesObserverMockito.onChanged(any()) } answers {}
        viewModel = MoviesListViewModel(dataRepository, TestCoroutineDispatcher())
        viewModel.movies.observeForever(moviesObserverMockito)
    }

    @After
    fun clear() {
        viewModel.movies.removeObserver(moviesObserverMockito)
    }

    @Test
    fun `should return error when repository throw exception`() = coroutineScope.dispatcher.runBlockingTest {
        val message = "Test"
        coEvery { dataRepository.getMoviesFromDB(any()) } throws Exception(message)

        viewModel.loadFirstPage()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.error(message))
        }
    }

    @Test
    fun `should return one success when repository return only API data`() =
        coroutineScope.dispatcher.runBlockingTest {
            val remote = Pair(1, listOf(mockkClass(Movie::class)))
            coEvery { dataRepository.getMoviesFromDB(any()) } returns emptyList()
            coEvery { dataRepository.getMoviesFromAPI(any(), any()) } returns remote

            viewModel.loadFirstPage()
            verifyOrder {
                moviesObserverMockito.onChanged(Resource.loading())
                moviesObserverMockito.onChanged(Resource.success(remote.second))
            }
        }

    @Test
    fun `should return separated success when repository return several data`() =
        coroutineScope.dispatcher.runBlockingTest {
            val local = listOf(mockkClass(Movie::class))
            val remote = Pair(1, listOf(mockkClass(Movie::class)))
            coEvery { dataRepository.getMoviesFromDB(any()) } returns local
            coEvery { dataRepository.getMoviesFromAPI(any(), any()) } returns remote

            viewModel.loadFirstPage()
            verifyOrder {
                moviesObserverMockito.onChanged(Resource.loading())
                moviesObserverMockito.onChanged(Resource.success(local))
                moviesObserverMockito.onChanged(Resource.success(remote.second))
            }
        }

    @Test
    fun `should return united success when load second page`() =
        coroutineScope.dispatcher.runBlockingTest {
            val first = Pair(2, listOf(mockkClass(Movie::class)))
            val second = Pair(2, listOf(mockkClass(Movie::class)))
            val local = listOf(mockkClass(Movie::class))
            coEvery { dataRepository.getMoviesFromDB(any()) } returns local
            coEvery { dataRepository.getMoviesFromAPI(any(), 1) } returns first
            coEvery { dataRepository.getMoviesFromAPI(any(), 2) } returns second

            viewModel.loadFirstPage()
            viewModel.loadMovies()
            verifyOrder {
                moviesObserverMockito.onChanged(Resource.loading())
                moviesObserverMockito.onChanged(Resource.success(local))
                moviesObserverMockito.onChanged(Resource.success(first.second + second.second))
            }
        }

    @Test
    fun `should not load next page if last page loaded`() =
        coroutineScope.dispatcher.runBlockingTest {
            val first = Pair(1, listOf(mockkClass(Movie::class)))
            coEvery { dataRepository.getMoviesFromDB(any()) } returns emptyList()
            coEvery { dataRepository.getMoviesFromAPI(any(), 1) } returns first

            viewModel.loadFirstPage()
            viewModel.loadMovies()
            verifyOrder {
                moviesObserverMockito.onChanged(Resource.loading())
                moviesObserverMockito.onChanged(Resource.success(first.second))
            }
        }
}
