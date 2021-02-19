package com.aacademy.homework.ui.movielist

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.extensions.hideKeyboard
import com.aacademy.homework.extensions.hideLoading
import com.aacademy.homework.extensions.setSafeOnClickListener
import com.aacademy.homework.extensions.showLoading
import com.aacademy.homework.extensions.startCircularReveal
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.foundations.Status.ERROR
import com.aacademy.homework.foundations.Status.LOADING
import com.aacademy.homework.foundations.Status.SUCCESS
import com.aacademy.homework.ui.activities.MainActivity
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesListViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    private val mainActivity by lazy { activity as MainActivity }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            view.startCircularReveal(
                it.getInt(POSITION_X),
                it.getInt(POSITION_Y),
                it.getFloat(RADIUS)
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
                { sharedView, movie ->
                    sharedView.hideKeyboard()
                    exitTransition = MaterialElevationScale(false).apply {
                        duration = 300L
                    }
                    reenterTransition = MaterialElevationScale(true).apply {
                        duration = 300L
                    }
                    mainActivity.openMovieDetail(sharedView, movie)
                },
                { id, isLiked ->
                    viewModel.setMovieLiked(id, isLiked)
                }
            ).apply { setHasStableIds(true) }

        binding.apply {
            postponeEnterTransition()
            root.doOnPreDraw { startPostponedEnterTransition() }

            initToolbar(toolbar)

            rvMovies.apply {
                setHasFixedSize(true)
                adapter = movieAdapter
                itemAnimator = MovieItemAnimator()

                addOnScrollListener(
                    object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            recyclerView.hideKeyboard()
                            if (manager.itemCount - (manager.findLastVisibleItemPosition() + 1) < 20) {
                                viewModel.loadMovies()
                            }
                        }
                    }
                )
            }
            swipeRefresh.setOnRefreshListener {
                movieAdapter.movies = emptyList()
                viewModel.loadFirstPage()
            }
            errorView.reloadListener = { viewModel.loadFirstPage() }
        }
    }

    private fun initToolbar(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.movie_list)
        val menu = toolbar.menu
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        if (viewModel.queryFlow.value.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(viewModel.queryFlow.value, true)
            searchView.clearFocus()
        }

        searchView.setOnQueryTextListener(
            object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    lifecycleScope.launch { viewModel.queryFlow.value = (query ?: "") }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (view?.hasWindowFocus() == true) {
                        lifecycleScope.launch { viewModel.queryFlow.value = newText ?: "" }
                    }
                    return false
                }
            }
        )
        menu.findItem(R.id.action_theme).setSafeOnClickListener {
            mainActivity.changeTheme(view?.findViewById(R.id.action_theme) ?: toolbar)
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
                        binding.rvMovies.visibility = VISIBLE
                        if (resource.data.isNullOrEmpty())
                            binding.emptyView.visibility = VISIBLE
                    }
                    ERROR -> {
                        hideLoading()
                        binding.swipeRefresh.isRefreshing = false
                        binding.errorView.visibility = VISIBLE
                        binding.rvMovies.visibility = GONE
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
