package com.aacademy.homework.ui.movielist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.extensions.hideLoading
import com.aacademy.homework.extensions.showLoading
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.foundations.Status.ERROR
import com.aacademy.homework.foundations.Status.LOADING
import com.aacademy.homework.foundations.Status.SUCCESS
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.views.DragManageAdapter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesListViewModel by viewModels()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // R.id.add -> movieAdapter.insertItem(getRandomMovie())
            R.id.remove -> movieAdapter.removeLastItem()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribe()
    }

    private fun initViews() {
        binding.apply {
            (activity as MainActivity).setSupportActionBar(toolbar)
            movieAdapter.setHasStableIds(true)
            rvMovies.apply {
                setHasFixedSize(true)
                adapter = movieAdapter
                itemAnimator = MovieItemAnimator()
            }
            ItemTouchHelper(DragManageAdapter(moveCallback = movieAdapter::swapItems)).attachToRecyclerView(rvMovies)
            swipeRefresh.setOnRefreshListener {
                movieAdapter.moviePreviews = emptyList()
                viewModel.refreshMoviesPreviews()
            }
            errorView.reloadListener = { viewModel.refreshMoviesPreviews() }
        }
    }

    private fun subscribe() {
        viewModel.moviesPreview.observe(
            viewLifecycleOwner,
            { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        hideLoading()
                        binding.swipeRefresh.isRefreshing = false
                        movieAdapter.moviePreviews = resource.data!!
                        if (resource.data.isNullOrEmpty())
                            binding.emptyView.visibility = VISIBLE
                    }
                    ERROR -> {
                        hideLoading()
                        binding.errorView.visibility = VISIBLE
                    }
                    LOADING -> {
                        binding.emptyView.visibility = GONE
                        binding.errorView.visibility = GONE
                        if (!binding.swipeRefresh.isRefreshing) {
                            showLoading()
                        }
                    }
                }
            }
        )
    }

    companion object {

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}
