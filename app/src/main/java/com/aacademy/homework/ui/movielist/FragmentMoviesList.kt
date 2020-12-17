package com.aacademy.homework.ui.movielist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentMoviesListBinding
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.activities.MoviesViewModel
import com.aacademy.homework.ui.views.DragManageAdapter
import com.aacademy.homework.utils.viewBinding
import com.bumptech.glide.Glide

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesViewModel by activityViewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val x = arguments?.getInt(POSITION_X)!!
            val y = arguments?.getInt(POSITION_Y)!!
            val rad = arguments?.getFloat(RADIUS)!!
            arguments = null

            view.addOnLayoutChangeListener(object : OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    v.removeOnLayoutChangeListener(this)
                    val anim = ViewAnimationUtils.createCircularReveal(v, x, y, 0f, rad)
                    anim.duration = 1000
                    anim.interpolator = AccelerateDecelerateInterpolator()
                    anim.addListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                v.isClickable = true
                            }

                            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                                super.onAnimationStart(animation, isReverse)
                                v.isClickable = false
                            }
                        }
                    )
                    anim.start()
                }
            })
        }

        initViews()
        subscribe()
    }

    private fun initViews() {
        movieAdapter =
            MovieAdapter(
                Glide.with(this),
                resources,
                {
                    (activity as MainActivity).openMovieDetail(it)
                },
                { id, isLiked ->
                    viewModel.setMovieLiked(id, isLiked)
                }
            ).apply { setHasStableIds(true) }

        binding.apply {
            (activity as MainActivity).setSupportActionBar(toolbar)
            toolbar.inflateMenu(R.menu.movie_list)
            rvMovies.apply {
                adapter = movieAdapter
                itemAnimator = MovieItemAnimator()
            }

            ItemTouchHelper(DragManageAdapter(moveCallback = viewModel::swapItems)).attachToRecyclerView(rvMovies)
        }
    }

    private fun subscribe() {
        viewModel.moviesPreview.observe(
            viewLifecycleOwner,
            {
                movieAdapter.moviePreviews = it
            }
        )
    }

    companion object {

        const val POSITION_X = "posX"
        const val POSITION_Y = "posY"
        const val RADIUS = "radius"

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }

        fun createBundle(posX: Int, posY: Int, radius: Float): Bundle {
            val arg = Bundle()
            arg.putInt(POSITION_X, posX)
            arg.putInt(POSITION_Y, posY)
            arg.putFloat(RADIUS, radius)
            return arg
        }
    }
}
