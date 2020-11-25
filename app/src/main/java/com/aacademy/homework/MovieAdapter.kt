package com.aacademy.homework

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.data.local.model.Movie
import com.bumptech.glide.RequestManager

class MovieAdapter(val glide: RequestManager, val resources: Resources) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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
            R.layout.layout_movie_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movie = movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.tvName)
        private val cover = itemView.findViewById<ImageView>(R.id.ivCover)
        private val ageLimit = itemView.findViewById<TextView>(R.id.tvAgeLimit)
        private val tags = itemView.findViewById<TextView>(R.id.tvTags)
        private val reviews = itemView.findViewById<TextView>(R.id.tvReviews)
        private val min = itemView.findViewById<TextView>(R.id.tvMin)
        private val like = itemView.findViewById<ImageView>(R.id.ivLike)
        private val rating = itemView.findViewById<RatingBarSvg>(R.id.rbRating)

        fun bind(movie: Movie) {
            name.text = movie.title
            glide.load(movie.coverPath).placeholder(R.drawable.orig).into(cover)
            ageLimit.text = resources.getString(R.string.ageLimitFormat).format(movie.ageLimit)
            tags.text = movie.tags.joinToString(", ")
            reviews.text = resources.getString(R.string.reviewsFormat).format(movie.reviews)
            rating.rating = movie.rating.toFloat()
            min.text = resources.getString(R.string.minFormat).format(movie.min)
            like.setImageResource(if (movie.isLiked) R.drawable.ic_like_filled else R.drawable.ic_like_empty)
        }
    }
}