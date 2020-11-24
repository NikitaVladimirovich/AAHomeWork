package com.aacademy.homework

import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.data.local.MockRepository
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        initViews()

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun initViews() {
        val movie = MockRepository.getMovie()

        val glide = Glide.with(this)
        glide.load(movie.coverPath).placeholder(R.drawable.orig).into(findViewById(R.id.ivCover))

        findViewById<TextView>(R.id.tvName).text = movie.title
        findViewById<TextView>(R.id.tvAgeLimit).text = getString(R.string.ageLimitFormat).format(movie.ageLimit)
        findViewById<TextView>(R.id.tvTags).text = movie.tags.joinToString(", ")
        findViewById<TextView>(R.id.tvReviews).text = getString(R.string.reviewsFormat).format(movie.reviews)
        findViewById<RatingBar>(R.id.rbRating).rating = movie.rating.toFloat()
        findViewById<TextView>(R.id.tvStoryline).text = movie.storyline

        val castAdapter = CastAdapter(glide)
        val rvCast = findViewById<RecyclerView>(R.id.rvCast)
        rvCast.apply {
            layoutManager = LinearLayoutManager(
                this@MovieDetailsActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = castAdapter
        }

        castAdapter.actors = movie.cast
    }
}