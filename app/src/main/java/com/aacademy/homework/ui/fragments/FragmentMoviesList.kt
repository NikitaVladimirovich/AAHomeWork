package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.aacademy.homework.R
import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.adapters.MovieAdapter
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide

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

        //TODO : only for test!!! need Rx or Coroutines
        Thread {
            val movies = Room.databaseBuilder(context!!, AppDatabase::class.java, "sqlite.db")
                .createFromAsset("sqlite.db")
                .build().movieDao().getAllMovies()
            Handler(Looper.getMainLooper()).post {
                movieAdapter.moviePreviews = movies
            }
        }.start()
    }

    companion object {

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}