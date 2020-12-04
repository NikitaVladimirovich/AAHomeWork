package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R
import com.aacademy.homework.data.local.MockRepository
import com.aacademy.homework.data.local.model.MoviePreviewWithTags
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.ui.adapters.CastAdapter
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glide = Glide.with(this)
        val moviePreview = arguments?.get(MOVIE_PREVIEW_ARGUMENT) as MoviePreviewWithTags

        val castAdapter = CastAdapter(glide)
        binding.rvCast.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = castAdapter
        }

        MockRepository.getMovieDetail(moviePreview.moviePreview.id)
            .subscribe({ movieDetail ->
                castAdapter.actors = movieDetail.cast
                binding.tvStoryline.text = movieDetail.movieDetail.storyline
            }, {
                it.printStackTrace()
            })

        glide.load(moviePreview.moviePreview.coverPath).into(binding.ivCover)

        binding.tvName.text = moviePreview.moviePreview.title
        binding.tvAgeLimit.text = getString(R.string.ageLimitFormat).format(moviePreview.moviePreview.ageLimit)
        binding.tvTags.text = moviePreview.tags.joinToString(", ") { it.name }
        binding.tvReviews.text = getString(R.string.reviewsFormat).format(moviePreview.moviePreview.reviews)
        binding.rbRating.rating = moviePreview.moviePreview.rating.toFloat()

        binding.tvBack.setOnClickListener { activity?.onBackPressed() }
    }

    companion object {

        private const val MOVIE_PREVIEW_ARGUMENT = "MoviePreview"

        fun newInstance(moviePreview: MoviePreviewWithTags): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable(MOVIE_PREVIEW_ARGUMENT, moviePreview)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}