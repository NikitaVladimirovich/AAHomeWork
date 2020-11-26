package com.aacademy.homework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.ui.adapters.CastAdapter
import com.aacademy.homework.R
import com.aacademy.homework.R.drawable
import com.aacademy.homework.R.layout
import com.aacademy.homework.R.string
import com.aacademy.homework.data.local.model.Movie
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.get(MOVIE_ARGUMENT) as Movie

        val glide = Glide.with(this)
        glide.load(movie.coverPath).placeholder(drawable.orig).into(view.findViewById(R.id.ivCover))

        view.findViewById<TextView>(R.id.tvName).text = movie.title
        view.findViewById<TextView>(R.id.tvAgeLimit).text = getString(string.ageLimitFormat).format(movie.ageLimit)
        view.findViewById<TextView>(R.id.tvTags).text = movie.tags.joinToString(", ")
        view.findViewById<TextView>(R.id.tvReviews).text = getString(string.reviewsFormat).format(movie.reviews)
        view.findViewById<RatingBar>(R.id.rbRating).rating = movie.rating.toFloat()
        view.findViewById<TextView>(R.id.tvStoryline).text = movie.storyline

        val castAdapter = CastAdapter(glide)
        val rvCast = view.findViewById<RecyclerView>(R.id.rvCast)
        rvCast.apply {
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