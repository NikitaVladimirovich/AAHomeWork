package com.aacademy.homework.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aacademy.homework.MainCoroutineScopeRule
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.foundations.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    lateinit var moviesObserverMockito: Observer<Resource<List<MoviePreview>>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { moviesObserverMockito.onChanged(any()) } answers {}
        coEvery { dataRepository.getAllPreviews() } returns emptyList()
        viewModel = MoviesListViewModel(dataRepository, TestCoroutineDispatcher())
        viewModel.moviesPreview.observeForever(moviesObserverMockito)
    }

    @After
    fun clear() {
        viewModel.moviesPreview.removeObserver(moviesObserverMockito)
    }

    @Test
    fun `should return empty data after init`() {
        verify {
            moviesObserverMockito.onChanged(Resource.success(emptyList()))
        }
    }

    @Test
    fun `should return error when repository throw exception`() {
        val message = "Test"
        coEvery { dataRepository.loadAllPreviews() } throws Exception(message)

        viewModel.refreshMoviesPreviews()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.error(message))
        }
    }

    @Test
    fun `should return success when repository return data`() {
        coEvery { dataRepository.loadAllPreviews() } returns emptyList()

        viewModel.refreshMoviesPreviews()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.success(emptyList()))
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.success(emptyList()))
        }
    }
}
