package com.aacademy.homework.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.activities.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FragmentMoviesDetails : Fragment() {

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)?.let {
            it.setSupportActionBar(binding.toolbar)
            it.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
        val glide = Glide.with(this)
        val movieId = arguments?.getInt(MOVIE_ID_ARGUMENT) ?: 0
        viewModel.getMovieDetail(movieId)

        val castAdapter = CastAdapter(glide)
        castAdapter.setHasStableIds(true)
        binding.rvCast.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = castAdapter
        }

        viewModel.moviesPreview.observe(viewLifecycleOwner) { moviePreviews ->
            moviePreviews.first { it.moviePreview.id == movieId }.let { moviePreview ->
                glide.load(moviePreview.moviePreview.backdrop)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivCover)


                binding.collapsingToolbar.title = moviePreview.moviePreview.title
                binding.tvAgeLimit.text =
                    getString(R.string.ageLimitFormat).format(moviePreview.moviePreview.ageLimit)
                binding.tvTags.text = moviePreview.genres.joinToString(", ") { it.name }
                binding.tvReviews.text = getString(R.string.reviewsFormat).format(moviePreview.moviePreview.reviews)
                binding.rbRating.rating = moviePreview.moviePreview.rating / 2
            }
        }

        viewModel.movieDetail.observe(viewLifecycleOwner) {
            if (it.movieDetail.id == movieId) {
                binding.tvStoryline.text = it.movieDetail.overview
                castAdapter.actors = it.actors
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
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