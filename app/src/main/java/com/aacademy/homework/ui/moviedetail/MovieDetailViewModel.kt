package com.aacademy.homework.ui.moviedetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import com.aacademy.homework.foundations.Resource
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails.Companion.MOVIE_PREVIEW_ARGUMENT
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val moviePreview: MoviePreviewWithGenres = savedStateHandle.get(MOVIE_PREVIEW_ARGUMENT)!!

    private val _movieDetail = MutableLiveData<Resource<MovieDetailWithActors>>()
    val movieDetail: LiveData<Resource<MovieDetailWithActors>> = _movieDetail

    private val detailExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _movieDetail.postValue(Resource.error(throwable.message ?: ""))
    }

    init {
        reloadData()
    }

    fun reloadData() {
        viewModelScope.launch(Dispatchers.IO + detailExceptionHandler) {
            _movieDetail.postValue(Resource.loading())
            _movieDetail.postValue(Resource.success(dataRepository.getMovieDetail(moviePreview.moviePreview.id)))
        }
    }
}
