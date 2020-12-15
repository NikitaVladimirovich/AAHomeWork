package com.aacademy.homework.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.activities.MoviesViewModel
import com.aacademy.homework.ui.views.DragManageAdapter
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesViewModel by activityViewModels()

    private val movieAdapter by lazy {
        MovieAdapter(
            Glide.with(this),
            resources,
            {
                (activity as MainActivity).openMovieDetail(it)
            },
            { id, isLiked ->
                viewModel.setMovieLiked(id, isLiked)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribe()
    }

    private fun initViews() {
        binding.apply {
            (activity as MainActivity).setSupportActionBar(toolbar)
            toolbar.inflateMenu(R.menu.movie_list)
            movieAdapter.setHasStableIds(true)
            rvMovies.apply {
                setHasFixedSize(true)
                adapter = movieAdapter
                itemAnimator = MovieItemAnimator()
            }

            ItemTouchHelper(DragManageAdapter(moveCallback = movieAdapter::swapItems)).attachToRecyclerView(rvMovies)
        }
    }

    private fun subscribe() {
        viewModel.moviesPreview.observe(
            viewLifecycleOwner,
            {
                movieAdapter.moviePreviews = it
            }
        )
    }

    companion object {

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}
