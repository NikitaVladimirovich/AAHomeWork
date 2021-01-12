package com.aacademy.homework.ui.movielist

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.extensions.hideLoading
import com.aacademy.homework.extensions.showLoading
import com.aacademy.homework.extensions.startCircularReveal
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

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            view.startCircularReveal(
                arguments?.getInt(POSITION_X)!!,
                arguments?.getInt(POSITION_Y)!!,
                arguments?.getFloat(RADIUS)!!
            )
            arguments = null
        }

        initViews()
        subscribe()
    }

    private fun initViews() {
        movieAdapter =
            MovieAdapter(
                Glide.with(this),
                resources,
                {
                    (activity as MainActivity).openMovieDetail(it)
                },
                { id, isLiked ->
                    viewModel.setMovieLiked(id, isLiked)
                }
            ).apply { setHasStableIds(true) }

        binding.apply {
            (activity as MainActivity).setSupportActionBar(toolbar)
            toolbar.inflateMenu(R.menu.movie_list)
            rvMovies.apply {
                setHasFixedSize(true)
                adapter = movieAdapter
                itemAnimator = MovieItemAnimator()

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (manager.itemCount - (manager.findLastVisibleItemPosition() + 1) < 20) {
                            viewModel.loadMovies()
                        }
                    }
                })
            }
            ItemTouchHelper(DragManageAdapter(moveCallback = viewModel::swapItems)).attachToRecyclerView(rvMovies)
            swipeRefresh.setOnRefreshListener {
                movieAdapter.movies = emptyList()
                viewModel.refreshMoviesPreviews()
            }
            errorView.reloadListener = { viewModel.refreshMoviesPreviews() }
        }
    }

    private fun subscribe() {
        viewModel.movies.observe(
            viewLifecycleOwner,
            { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        hideLoading()
                        binding.swipeRefresh.isRefreshing = false
                        movieAdapter.movies = resource.data!!
                        if (resource.data.isNullOrEmpty())
                            binding.emptyView.visibility = VISIBLE
                    }
                    ERROR -> {
                        hideLoading()
                        binding.swipeRefresh.isRefreshing = false
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

        const val POSITION_X = "posX"
        const val POSITION_Y = "posY"
        const val RADIUS = "radius"

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }

        fun createBundle(posX: Int, posY: Int, radius: Float): Bundle {
            val arg = Bundle()
            arg.putInt(POSITION_X, posX)
            arg.putInt(POSITION_Y, posY)
            arg.putFloat(RADIUS, radius)
            return arg
        }
    }
}
