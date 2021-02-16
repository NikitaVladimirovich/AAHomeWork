package com.aacademy.homework.ui.moviedetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aacademy.homework.R
import com.aacademy.homework.R.string
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.databinding.FragmentMoviesDetailsBinding
import com.aacademy.homework.extensions.hideLoading
import com.aacademy.homework.extensions.loadImage
import com.aacademy.homework.extensions.setSafeOnClickListener
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
import java.util.Calendar
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
            errorView.reloadListener = { viewModel.reloadData() }
            fabCalendar.setSafeOnClickListener {
                val calendar = Calendar.getInstance()
                context?.let {
                    val datePickerDialog = DatePickerDialog(
                        it,
                        R.style.DateTimePickerDialog,
                        { _, year, month, dayOfMonth ->
                            val timePickerDialog =
                                TimePickerDialog(
                                    context,
                                    R.style.DateTimePickerDialog,
                                    { _, hourOfDay, minute ->
                                        val selectedDate = Calendar.getInstance()
                                        selectedDate.set(Calendar.YEAR, year)
                                        selectedDate.set(Calendar.MONTH, month)
                                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        selectedDate.set(Calendar.MINUTE, minute)
                                        val intent = Intent(Intent.ACTION_INSERT)
                                            .setData(CalendarContract.Events.CONTENT_URI)
                                            .putExtra(
                                                CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                                selectedDate.timeInMillis
                                            )
                                            .putExtra(CalendarContract.Events.TITLE, viewModel.movie.title)
                                            .putExtra(CalendarContract.Events.DESCRIPTION, viewModel.movie.overview)
                                            .putExtra(
                                                CalendarContract.Events.EVENT_LOCATION,
                                                getString(string.cinema)
                                            )
                                            .putExtra(
                                                CalendarContract.Events.AVAILABILITY,
                                                CalendarContract.Events.AVAILABILITY_BUSY
                                            )
                                        startActivity(intent)
                                    },
                                    calendar[Calendar.HOUR_OF_DAY],
                                    calendar[Calendar.MINUTE],
                                    true
                                )
                            timePickerDialog.show()
                        },
                        calendar[Calendar.YEAR],
                        calendar[Calendar.MONTH],
                        calendar[Calendar.DAY_OF_MONTH]
                    )
                    datePickerDialog.show()
                }
            }
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

        const val MOVIE_ARGUMENT = "Movie"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable(MOVIE_ARGUMENT, movie)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(arguments: Bundle): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = arguments
            return fragment
        }
    }
}
