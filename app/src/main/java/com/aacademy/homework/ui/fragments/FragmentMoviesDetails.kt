package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.utils.viewBinding

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val movie = arguments?.get(MOVIE_ID_ARGUMENT) as Movie

//        val glide = Glide.with(this)
//        glide.load(movie.coverPath).into(binding.ivCover)
//
//        binding.tvName.text = movie.title
//        binding.tvAgeLimit.text = getString(string.ageLimitFormat).format(movie.ageLimit)
//        binding.tvTags.text = movie.tags.joinToString(", ")
//        binding.tvReviews.text = getString(string.reviewsFormat).format(movie.reviews)
//        binding.rbRating.rating = movie.rating.toFloat()
//        binding.tvStoryline.text = movie.storyline
//
//        val castAdapter = CastAdapter(glide)
//        binding.rvCast.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            setHasFixedSize(true)
//            adapter = castAdapter
//        }
//
//        castAdapter.actors = movie.cast
//
//        binding.tvBack.setOnClickListener { activity?.onBackPressed() }
    }

    companion object {

        private const val MOVIE_ID_ARGUMENT = "MovieIdArgument"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val args = Bundle()
            args.putInt(MOVIE_ID_ARGUMENT, movieId)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}