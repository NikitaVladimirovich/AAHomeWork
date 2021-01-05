package com.aacademy.homework.ui.moviedetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.foundations.Resource
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails.Companion.MOVIE_PREVIEW_ARGUMENT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val movie: Movie = savedStateHandle.get(MOVIE_PREVIEW_ARGUMENT)!!

    private val _cast = MutableLiveData<Resource<List<Actor>>>()
    val cast: LiveData<Resource<List<Actor>>> = _cast

    private val detailExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _cast.postValue(Resource.error(throwable.message ?: ""))
    }

    init {
        reloadData()
    }

    fun reloadData() {
        viewModelScope.launch(dispatcher + detailExceptionHandler) {
            _cast.postValue(Resource.loading())
            _cast.postValue(Resource.success(dataRepository.getCast(movie.id)))
        }
    }
}
