package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R.drawable
import com.aacademy.homework.R.string
import com.aacademy.homework.data.local.model.Movie
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.ui.adapters.CastAdapter
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment() {

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.get(MOVIE_ARGUMENT) as Movie

        val glide = Glide.with(this)
        glide.load(movie.coverPath).placeholder(drawable.orig).into(binding.ivCover)

        binding.tvName.text = movie.title
        binding.tvAgeLimit.text = getString(string.ageLimitFormat).format(movie.ageLimit)
        binding.tvTags.text = movie.tags.joinToString(", ")
        binding.tvReviews.text = getString(string.reviewsFormat).format(movie.reviews)
        binding.rbRating.rating = movie.rating.toFloat()
        binding.tvStoryline.text = movie.storyline

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

        castAdapter.actors = movie.cast
    }

    companion object {

        private const val MOVIE_ARGUMENT = "MovieArgument"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable(MOVIE_ARGUMENT, movie)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}