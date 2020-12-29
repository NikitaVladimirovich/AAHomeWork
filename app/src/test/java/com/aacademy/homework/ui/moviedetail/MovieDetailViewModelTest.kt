package com.aacademy.homework.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.aacademy.homework.MainCoroutineScopeRule
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.foundations.Resource
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails.Companion.MOVIE_PREVIEW_ARGUMENT
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
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
    lateinit var moviesObserverMockito: Observer<Resource<MovieDetail>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { moviesObserverMockito.onChanged(any()) } answers {}
        savedStateHandle = SavedStateHandle()
        val moviePreview = mockkClass(MoviePreview::class)
        every { moviePreview.id } returns 0
        savedStateHandle.set(MOVIE_PREVIEW_ARGUMENT, moviePreview)
        viewModel = MovieDetailViewModel(dataRepository, savedStateHandle, TestCoroutineDispatcher())
    }

    @Test
    fun `should return error when repository throw exception`() {
        val message = "this is a test"
        coEvery { dataRepository.getMovieDetail(any()) } throws Exception(message)

        viewModel.movieDetail.observeForever(moviesObserverMockito)
        viewModel.reloadData()
        verify {
            moviesObserverMockito.onChanged(Resource.error(message))
        }
        viewModel.movieDetail.removeObserver(moviesObserverMockito)
    }

    @Test
    fun `should return success when repository return data`() {
        val data = mockkClass(MovieDetail::class)
        coEvery { dataRepository.getMovieDetail(any()) } returns data

        viewModel.movieDetail.observeForever(moviesObserverMockito)
        viewModel.reloadData()
        verify {
            moviesObserverMockito.onChanged(Resource.success(data))
        }
        viewModel.movieDetail.removeObserver(moviesObserverMockito)
    }
}
