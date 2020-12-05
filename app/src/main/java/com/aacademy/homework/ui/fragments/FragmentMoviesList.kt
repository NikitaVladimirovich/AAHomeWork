package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aacademy.homework.R
import com.aacademy.homework.data.local.MockRepository
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.adapters.MovieAdapter
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter(Glide.with(this), resources) {
            (activity as MainActivity?)?.openMovieDetail(it)
        }
        binding.rvMovies.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        MockRepository.getAllMoviePreviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieAdapter.moviePreviews = it
            }, {
                Timber.e(it, "Error when load movie previews")
            })
    }

    companion object {

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}