package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R
import com.aacademy.homework.R.integer
import com.aacademy.homework.R.layout
import com.aacademy.homework.data.local.MockRepository
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.adapters.MovieAdapter
import com.bumptech.glide.Glide

class FragmentMoviesList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter(Glide.with(this), resources) {
            (activity as MainActivity?)?.openMovieDetail(it)
        }
        val rvMovies = view.findViewById<RecyclerView>(R.id.rvMovies)
        rvMovies.apply {
            layoutManager = GridLayoutManager(context, resources.getInteger(integer.moviesSpanCount))
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        movieAdapter.movies = MockRepository.getMovies()
    }

    companion object {

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}