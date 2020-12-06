package com.aacademy.homework.ui.moviedetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R
import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.MoviePreviewWithTags
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val binding by viewBinding(FragmentMoviesDetailsBinding::bind)
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

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
        val moviePreview = arguments?.get(MOVIE_PREVIEW_ARGUMENT) as MoviePreviewWithTags

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

        compositeDisposable.add(FakeLocalRepository.getMovieDetail(moviePreview.moviePreview.id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                castAdapter.actors = it.cast
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieDetail ->
                binding.tvStoryline.text = movieDetail.movieDetail.storyline
            }, {
                Timber.e(it, "Error when load movie detail")
            })
        )

        glide.load(moviePreview.moviePreview.coverPath).into(binding.ivCover)

        binding.collapsingToolbar.title = moviePreview.moviePreview.title
        binding.tvAgeLimit.text = getString(R.string.ageLimitFormat).format(moviePreview.moviePreview.ageLimit)
        binding.tvTags.text = moviePreview.tags.joinToString(", ") { it.name }
        binding.tvReviews.text = getString(R.string.reviewsFormat).format(moviePreview.moviePreview.reviews)
        binding.rbRating.rating = moviePreview.moviePreview.rating.toFloat()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
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