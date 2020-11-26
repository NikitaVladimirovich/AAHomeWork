package com.aacademy.homework.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R.drawable
import com.aacademy.homework.R.id
import com.aacademy.homework.R.layout
import com.aacademy.homework.R.string
import com.aacademy.homework.data.local.model.Movie
import com.aacademy.homework.ui.adapters.MovieAdapter.MovieViewHolder
import com.aacademy.homework.ui.views.RatingBarSvg
import com.bumptech.glide.RequestManager

class MovieAdapter(val glide: RequestManager, val resources: Resources) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var movies: List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(
            layout.layout_movie_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movie = movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(id.tvName)
        private val cover = itemView.findViewById<ImageView>(id.ivCover)
        private val ageLimit = itemView.findViewById<TextView>(id.tvAgeLimit)
        private val tags = itemView.findViewById<TextView>(id.tvTags)
        private val reviews = itemView.findViewById<TextView>(id.tvReviews)
        private val min = itemView.findViewById<TextView>(id.tvMin)
        private val like = itemView.findViewById<ImageView>(id.ivLike)
        private val rating = itemView.findViewById<RatingBarSvg>(id.rbRating)

        fun bind(movie: Movie) {
            name.text = movie.title
            glide.load(movie.coverPath).placeholder(drawable.orig).into(cover)
            ageLimit.text = resources.getString(string.ageLimitFormat).format(movie.ageLimit)
            tags.text = movie.tags.joinToString(", ")
            reviews.text = resources.getString(string.reviewsFormat).format(movie.reviews)
            rating.rating = movie.rating.toFloat()
            min.text = resources.getString(string.minFormat).format(movie.min)
            like.setImageResource(if (movie.isLiked) drawable.ic_like_filled else drawable.ic_like_empty)
        }
    }
}