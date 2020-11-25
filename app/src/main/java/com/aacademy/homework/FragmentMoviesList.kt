package com.aacademy.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.data.local.MockRepository
import com.bumptech.glide.Glide

class FragmentMoviesList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter(Glide.with(this), resources)
        val rvMovies = view.findViewById<RecyclerView>(R.id.rvMovies)
        rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
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