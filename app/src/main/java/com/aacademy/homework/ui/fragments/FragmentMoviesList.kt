package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aacademy.homework.R.integer
import com.aacademy.homework.data.local.MockRepository
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.adapters.MovieAdapter
import com.bumptech.glide.Glide

class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter(Glide.with(this), resources) {
            (activity as MainActivity?)?.openMovieDetail(it)
        }
        binding.rvMovies.apply {
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