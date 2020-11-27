package com.aacademy.homework.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R.string
import com.aacademy.homework.data.local.model.Movie
import com.aacademy.homework.databinding.LayoutMovieItemBinding
import com.aacademy.homework.ui.adapters.MovieAdapter.MovieViewHolder
import com.bumptech.glide.RequestManager

class MovieAdapter(val glide: RequestManager, val resources: Resources, val clickListener: (Movie) -> Unit) :
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
        LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movie = movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: LayoutMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvName.text = movie.title
            glide.load(movie.coverPath).into(binding.ivCover)
            binding.tvAgeLimit.text = resources.getString(string.ageLimitFormat).format(movie.ageLimit)
            binding.tvTags.text = movie.tags.joinToString(", ")
            binding.tvReviews.text = resources.getString(string.reviewsFormat).format(movie.reviews)
            binding.rbRating.rating = movie.rating.toFloat()
            binding.tvMin.text = resources.getString(string.minFormat).format(movie.min)
            binding.cbLike.isSelected = movie.isLiked
            binding.cbLike.setOnCheckedChangeListener { _, isChecked -> movie.isLiked = isChecked }
            binding.root.setOnClickListener { clickListener(movie) }
        }
    }
}