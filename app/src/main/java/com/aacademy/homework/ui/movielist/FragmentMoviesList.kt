package com.aacademy.homework.ui.movielist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.activities.MainViewModel
import com.aacademy.homework.ui.views.DragManageAdapter
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: MainViewModel by activityViewModels()

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
            //R.id.add -> movieAdapter.insertItem(getRandomMovie())
            R.id.remove -> movieAdapter.removeLastItem()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)?.setSupportActionBar(binding.toolbar)
        movieAdapter = MovieAdapter(Glide.with(this), resources, {
            (activity as MainActivity?)?.openMovieDetail(it)
        }, { id, isLiked ->
            viewModel.setMovieLiked(id, isLiked)
        })
        movieAdapter.setHasStableIds(true)
        binding.rvMovies.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
            itemAnimator = MovieItemAnimator()
        }

        ItemTouchHelper(DragManageAdapter(moveCallback = movieAdapter::swapItems)).attachToRecyclerView(
            binding.rvMovies
        )

        viewModel.moviesPreview.observe(viewLifecycleOwner, {
            movieAdapter.moviePreviews = it
        })
    }

    companion object {

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}