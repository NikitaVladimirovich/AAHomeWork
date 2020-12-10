package com.aacademy.homework.ui.moviedetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.activities.MainViewModel
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FragmentMoviesDetails : Fragment() {

    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)
    private val viewModel: MainViewModel by activityViewModels()
    private val glide by lazy { Glide.with(this) }
    private val castAdapter by lazy { CastAdapter(glide) }

    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        movieId = arguments?.getInt(MOVIE_ID_ARGUMENT) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState ?: viewModel.getMovieDetail(movieId)
        initViews()
        subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViews() {
        binding.apply {
            (activity as MainActivity?)?.let {
                it.setSupportActionBar(toolbar)
                it.supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            }

            castAdapter.setHasStableIds(true)
            rvCast.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
                adapter = castAdapter
            }
        }
    }

    private fun subscribe() {
        viewModel.moviesPreview.observe(viewLifecycleOwner) { moviePreviews ->
            moviePreviews.first { it.moviePreview.id == movieId }.let { moviePreview ->
                binding.apply {
                    glide.load(moviePreview.moviePreview.backdrop)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivCover)
                    collapsingToolbar.title = moviePreview.moviePreview.title
                    tvAgeLimit.text =
                        getString(R.string.ageLimitFormat).format(moviePreview.moviePreview.ageLimit)
                    tvTags.text = moviePreview.genres.joinToString(", ") { it.name }
                    tvReviews.text = getString(R.string.reviewsFormat).format(moviePreview.moviePreview.reviews)
                    rbRating.rating = moviePreview.moviePreview.rating / 2
                }
            }
        }

        viewModel.movieDetail.observe(viewLifecycleOwner) {
            if (it.movieDetail.id == movieId) {
                binding.tvStoryline.text = it.movieDetail.overview
                castAdapter.actors = it.actors
            }
        }
    }

    companion object {

        private const val MOVIE_ID_ARGUMENT = "MovieId"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val args = Bundle()
            args.putInt(MOVIE_ID_ARGUMENT, movieId)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}