package com.aacademy.homework.ui.moviedetail

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.extensions.hideLoading
import com.aacademy.homework.extensions.loadImage
import com.aacademy.homework.extensions.showLoading
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.foundations.Status.ERROR
import com.aacademy.homework.foundations.Status.LOADING
import com.aacademy.homework.foundations.Status.SUCCESS
import com.aacademy.homework.ui.activities.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class FragmentMoviesDetails @Inject constructor() : Fragment(R.layout.fragment_movies_details) {

    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)
    private val viewModel: MovieDetailViewModel by viewModels()

    private val glide by lazy { Glide.with(this) }
    private val castAdapter by lazy { CastAdapter(glide) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribe()
    }

    private fun initViews() {
        binding.apply {
            initToolbar(toolbar)
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
            binding.errorView.reloadListener = { viewModel.reloadData() }
        }
    }

    private fun initToolbar(toolbar: Toolbar) {
        (activity as MainActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun subscribe() {
        viewModel.movie.let { moviePreview ->
            binding.apply {
                glide.loadImage(moviePreview.backdrop)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(ivCover)
                collapsingToolbar.title = moviePreview.title
                tvAgeLimit.text =
                    getString(R.string.ageLimitFormat).format(moviePreview.ageLimit)
                tvTags.text = moviePreview.genres.joinToString(", ") { it.name }
                tvReviews.text =
                    getString(R.string.reviewsFormat).format(moviePreview.reviews)
                rbRating.rating = moviePreview.rating / 2
                binding.tvStoryline.text = moviePreview.overview
            }
        }

        viewModel.cast.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                SUCCESS -> {
                    hideLoading()
                    resource.data?.let {
                        binding.tvStorylineHeader.visibility = VISIBLE
                        binding.tvStoryline.visibility = VISIBLE
                        binding.rvCast.visibility = VISIBLE
                        if (it.isNotEmpty()) {
                            binding.tvCast.visibility = VISIBLE
                            castAdapter.actors = it
                        } else
                            binding.tvCast.visibility = GONE
                    }
                }
                ERROR -> {
                    hideLoading()
                    binding.tvStorylineHeader.visibility = GONE
                    binding.tvStoryline.visibility = GONE
                    binding.rvCast.visibility = GONE
                    binding.tvCast.visibility = GONE
                    binding.errorView.visibility = VISIBLE
                }
                LOADING -> {
                    binding.errorView.visibility = GONE
                    showLoading()
                }
            }
        }
    }

    companion object {

        const val MOVIE_PREVIEW_ARGUMENT = "MoviePreview"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable(MOVIE_PREVIEW_ARGUMENT, movie)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}
