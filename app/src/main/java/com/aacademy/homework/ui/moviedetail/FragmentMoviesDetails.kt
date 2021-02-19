package com.aacademy.homework.ui.moviedetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialContainerTransform
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

    private val mainActivity by lazy { activity as MainActivity }

    private val glide by lazy { Glide.with(this) }
    private val castAdapter by lazy { CastAdapter(glide) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            // Scope the transition to a view in the hierarchy so we know it will be added under
            // the bottom app bar but over the elevation scale of the exiting HomeFragment.
            drawingViewId = R.id.nav_host_fragment
            duration = 300L
//            scrimColor = Color.TRANSPARENT
//            context?.let {
//                setAllContainerColors(MaterialColors.getColor(it, R.attr.colorBackground, Color.TRANSPARENT))
//            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribe()
    }

    private fun initViews() {
        binding.apply {
            postponeEnterTransition()
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
            fabCalendar.setSafeOnClickListener(::addMovieToCalendar)
        }
    }

    private fun initToolbar(toolbar: Toolbar) {
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun subscribe() {
        viewModel.movie.let { moviePreview ->
            binding.apply {
                glide.loadImage(moviePreview.backdrop)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .listener(
                        object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                startPostponedEnterTransition()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                startPostponedEnterTransition()
                                return false
                            }
                        }
                    ).apply(
                        RequestOptions().dontTransform()
                    )
                    .into(ivCover)
                collapsingToolbar.title = moviePreview.title
                tvAgeLimit.text =
                    getString(string.ageLimitFormat).format(moviePreview.ageLimit)
                tvTags.text = moviePreview.genres.joinToString(", ") { it.name }
                tvReviews.text =
                    getString(string.reviewsFormat).format(moviePreview.reviews)
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

    private fun openDatePicker(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        dateSelectListener: (View, Int, Int, Int) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(
            mainActivity,
            R.style.DateTimePickerDialog,
            dateSelectListener,
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun openTimePicker(hourOfDay: Int, minute: Int, timeSelectListener: (View, Int, Int) -> Unit) {
        val timePickerDialog =
            TimePickerDialog(
                mainActivity,
                R.style.DateTimePickerDialog,
                timeSelectListener,
                hourOfDay,
                minute,
                true
            )
        timePickerDialog.show()
    }

    private fun openCalendar(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int, minute: Int
    ) {
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
    }

    private fun addMovieToCalendar() {
        val calendar = Calendar.getInstance()
        context?.let {
            openDatePicker(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ) { _, year, month, dayOfMonth ->
                openTimePicker(
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE]
                ) { _, hourOfDay, minute ->
                    openCalendar(year, month, dayOfMonth, hourOfDay, minute)
                }
            }
        }
    }

    companion object {

        const val MOVIE_ARGUMENT = "Movie"

        fun newInstance(arguments: Bundle): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = arguments
            return fragment
        }
    }
}
