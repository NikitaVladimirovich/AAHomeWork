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
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
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
        coEvery { dataRepository.getMovies(any()) } returns flow { emit(Triple(true, 1, emptyList<Movie>())) }
        viewModel = MoviesListViewModel(dataRepository, TestCoroutineDispatcher())
        viewModel.movies.observeForever(moviesObserverMockito)
    }

    @After
    fun clear() {
        viewModel.movies.removeObserver(moviesObserverMockito)
    }

//    @Test
//    fun `should return empty data after init`() {
//        verify {
//            moviesObserverMockito.onChanged(Resource.success(emptyList()))
//        }
//    }

    @Test
    fun `should return error when repository throw exception`() = coroutineScope.dispatcher.runBlockingTest {
        val message = "Test"
        coEvery { dataRepository.getMovies(any()) } throws Exception(message)

        viewModel.loadFirstPage()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.error(message))
        }
    }

    @Test
    fun `should return success when repository return data`() = coroutineScope.dispatcher.runBlockingTest {
        coEvery { dataRepository.getMovies() } returns flow { emit(Triple(true, 1, emptyList<Movie>())) }

        viewModel.loadFirstPage()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.success(emptyList()))
        }
    }
}
