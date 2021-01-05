package com.aacademy.homework.ui.movielist

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R.string
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.databinding.LayoutMovieItemBinding
import com.aacademy.homework.extensions.loadImage
import com.aacademy.homework.ui.movielist.MovieAdapter.MovieViewHolder
import com.aacademy.homework.ui.movielist.MovieItemAnimator.LikeViewHolder
import com.bumptech.glide.RequestManager

class MovieAdapter(
    private val glide: RequestManager,
    private val resources: Resources,
    private val itemClickListener: (MoviePreview) -> Unit,
    private val likeStateChangeListener: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MoviePreview>() {
        override fun areItemsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var moviePreviews: List<MoviePreview>
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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviePreviews[position])
    }

    override fun getItemCount(): Int = moviePreviews.size

    override fun getItemId(position: Int): Long {
        return moviePreviews[position].id
    }

    inner class MovieViewHolder(private val binding: LayoutMovieItemBinding) : LikeViewHolder(binding) {

        fun bind(moviePreview: MoviePreview) {
            binding.tvName.text = moviePreview.title
            glide.loadImage(moviePreview.poster)
                .into(binding.ivCover)
            binding.tvAgeLimit.text =
                resources.getString(string.ageLimitFormat).format(moviePreview.ageLimit)
            binding.tvTags.text = moviePreview.genres.take(3).joinToString(", ") { it.name }
            binding.tvReviews.text =
                resources.getString(string.reviewsFormat).format(moviePreview.reviews)
            binding.rbRating.rating = moviePreview.rating / 2
            binding.tvMin.text = resources.getString(string.minFormat).format(moviePreview.runtime)
            binding.cbLike.setOnCheckedChangeListener(null)
            binding.cbLike.isChecked = moviePreview.isLiked
            binding.cbLike.setOnCheckedChangeListener { _, isChecked ->
                moviePreview.isLiked = isChecked
                likeStateChangeListener.invoke(moviePreview.id, isChecked)
                if (isChecked) notifyItemChanged(adapterPosition, MovieItemAnimator.ACTION_FILM_LIKED)
            }
            binding.llLike.setOnClickListener {
                binding.cbLike.isChecked = !moviePreview.isLiked
            }
            binding.root.setOnClickListener { itemClickListener(moviePreview) }
        }

        override val ivLike: View
            get() = binding.ivLike
    }
}
