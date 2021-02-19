package com.aacademy.homework.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.foundations.Resource
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails.Companion.MOVIE_ARGUMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val movie: Movie = savedStateHandle.get(MOVIE_ARGUMENT)!!

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
            val dbActors = dataRepository.getCastFromDB(movie.id)
            if (dbActors.isNotEmpty()) _cast.postValue(Resource.success(dbActors))
            _cast.postValue(Resource.success(dataRepository.getCastFromAPI(movie.id)))
        }
    }
}
