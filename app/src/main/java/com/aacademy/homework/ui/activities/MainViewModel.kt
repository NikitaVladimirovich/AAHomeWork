package com.aacademy.homework.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.FakeDataRepository
import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _moviesPreview = MutableLiveData<List<MoviePreviewWithGenres>>()
    val moviesPreview: LiveData<List<MoviePreviewWithGenres>> = _moviesPreview

    private val _movieDetail = MutableLiveData<MovieDetailWithActors>()
    val movieDetail: LiveData<MovieDetailWithActors> = _movieDetail

    init {
        viewModelScope.launch {
            _moviesPreview.postValue(FakeDataRepository.getAllPreviews())
        }
    }

    fun getMovieDetail(id: Int) {
        if (movieDetail.value?.movieDetail?.id != id) {
            viewModelScope.launch(Dispatchers.IO) {
                _movieDetail.postValue(FakeDataRepository.getMovieDetail(id))
            }
        }
    }

    fun setMovieLiked(id: Int, isLiked: Boolean) {
        viewModelScope.launch {
            FakeLocalRepository.setMovieLiked(id, isLiked)
        }
    }
}