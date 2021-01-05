package com.aacademy.homework.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.aacademy.homework.MainCoroutineScopeRule
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.foundations.Resource
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails.Companion.MOVIE_PREVIEW_ARGUMENT
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel

    @MockK
    private lateinit var dataRepository: DataRepository

    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    lateinit var moviesObserverMockito: Observer<Resource<List<Actor>>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { moviesObserverMockito.onChanged(any()) } answers {}
        savedStateHandle = SavedStateHandle()
        val moviePreview = mockkClass(Movie::class)
        every { moviePreview.id } returns 0
        savedStateHandle.set(MOVIE_PREVIEW_ARGUMENT, moviePreview)
        coEvery { dataRepository.getCast(any()) } throws Exception()
        viewModel = MovieDetailViewModel(dataRepository, savedStateHandle, TestCoroutineDispatcher())
        viewModel.cast.observeForever(moviesObserverMockito)
    }

    @After
    fun clear() {
        viewModel.cast.removeObserver(moviesObserverMockito)
    }

    @Test
    fun `should return error after init`() {
        verify {
            moviesObserverMockito.onChanged(Resource.error(""))
        }
    }

    @Test
    fun `should return error when repository throw exception`() {
        val message = "Test"
        coEvery { dataRepository.getCast(any()) } throws Exception(message)

        viewModel.reloadData()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.error(message))
        }
    }

    @Test
    fun `should return success when repository return data`() {
        val data = emptyList<Actor>()
        coEvery { dataRepository.getCast(any()) } returns data

        viewModel.reloadData()
        verifyOrder {
            moviesObserverMockito.onChanged(Resource.loading())
            moviesObserverMockito.onChanged(Resource.success(data))
        }
    }
}
