package com.aacademy.homework.ui.movielist

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R
import com.aacademy.homework.R.string
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.databinding.LayoutMovieItemBinding
import com.aacademy.homework.extensions.loadImage
import com.aacademy.homework.extensions.setSafeOnClickListener
import com.aacademy.homework.ui.movielist.MovieAdapter.MovieViewHolder
import com.aacademy.homework.ui.movielist.MovieItemAnimator.LikeViewHolder
import com.bumptech.glide.RequestManager

class MovieAdapter(
    private val glide: RequestManager,
    private val resources: Resources,
    private val itemClickListener: (View, Movie) -> Unit,
    private val likeStateChangeListener: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(movies[position])

    override fun getItemCount(): Int = movies.size

    override fun getItemId(position: Int): Long = movies[position].id

    inner class MovieViewHolder(private val binding: LayoutMovieItemBinding) : LikeViewHolder(binding) {

        fun bind(movie: Movie) {
            binding.tvName.text = movie.title
            glide.loadImage(movie.poster)
                .placeholder(R.drawable.film_poster_placeholder)
                .into(binding.ivCover)
            binding.tvAgeLimit.text =
                resources.getString(string.ageLimitFormat).format(movie.ageLimit)
            binding.tvTags.text = movie.genres.take(3).joinToString(", ") { it.name }
            binding.tvReviews.text =
                resources.getString(string.reviewsFormat).format(movie.reviews)
            binding.rbRating.rating = movie.rating / 2
            binding.tvMin.text = resources.getString(string.minFormat).format(movie.runtime)
            binding.cbLike.setOnCheckedChangeListener(null)
            binding.cbLike.isChecked = movie.isLiked
            binding.cbLike.setOnCheckedChangeListener { _, isChecked ->
                movie.isLiked = isChecked
                likeStateChangeListener.invoke(movie.id, isChecked)
                if (isChecked) notifyItemChanged(adapterPosition, MovieItemAnimator.ACTION_FILM_LIKED)
            }
            binding.llLike.setSafeOnClickListener {
                binding.cbLike.isChecked = !movie.isLiked
            }
            binding.root.transitionName = binding.root.context.getString(string.movie_card_transition_name, movie.id)
            binding.root.setSafeOnClickListener { itemClickListener(binding.root, movie) }
        }

        override val ivLike: View
            get() = binding.ivLike
    }
}
