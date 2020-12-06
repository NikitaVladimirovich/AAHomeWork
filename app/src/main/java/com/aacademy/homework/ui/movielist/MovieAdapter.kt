package com.aacademy.homework.ui.movielist

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R.string
import com.aacademy.homework.data.model.MoviePreviewWithTags
import com.aacademy.homework.databinding.LayoutMovieItemBinding
import com.aacademy.homework.ui.movielist.MovieAdapter.MovieViewHolder
import com.aacademy.homework.ui.movielist.MovieItemAnimator.LikeViewHolder
import com.bumptech.glide.RequestManager
import java.util.Collections

class MovieAdapter(
    val glide: RequestManager,
    val resources: Resources,
    val itemClickListener: (MoviePreviewWithTags) -> Unit,
    val likeStateChangeListener: (Int, Boolean) -> Unit
) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MoviePreviewWithTags>() {
        override fun areItemsTheSame(oldItem: MoviePreviewWithTags, newItem: MoviePreviewWithTags): Boolean =
            oldItem.moviePreview.id == newItem.moviePreview.id

        override fun areContentsTheSame(oldItem: MoviePreviewWithTags, newItem: MoviePreviewWithTags): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var moviePreviews: List<MoviePreviewWithTags>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviePreview = moviePreviews[position])
    }

    override fun getItemCount(): Int = moviePreviews.size

    fun swapItems(fromPosition: Int, toPosition: Int) {
        val newMovies = moviePreviews.toMutableList()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(newMovies, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo (toPosition + 1)) {
                Collections.swap(newMovies, i, i - 1)
            }
        }

        moviePreviews = newMovies
    }

    fun insertItem(movie: MoviePreviewWithTags) {
        val newMovies = moviePreviews.toMutableList()
        newMovies.add(movie)
        moviePreviews = newMovies
    }

    fun removeLastItem() {
        if (moviePreviews.isEmpty()) return
        val newMovies = moviePreviews.toMutableList()
        newMovies.removeLast()
        moviePreviews = newMovies
        notifyItemRemoved(moviePreviews.size)
    }

    override fun getItemId(position: Int): Long {
        return moviePreviews[position].moviePreview.id.toLong()
    }

    inner class MovieViewHolder(private val binding: LayoutMovieItemBinding) : LikeViewHolder(binding) {

        fun bind(moviePreview: MoviePreviewWithTags) {
            binding.tvName.text = moviePreview.moviePreview.title
            glide.load(moviePreview.moviePreview.coverPath).into(binding.ivCover)
            binding.tvAgeLimit.text =
                resources.getString(string.ageLimitFormat).format(moviePreview.moviePreview.ageLimit)
            binding.tvTags.text = moviePreview.tags.joinToString(", ") { it.name }
            binding.tvReviews.text =
                resources.getString(string.reviewsFormat).format(moviePreview.moviePreview.reviews)
            binding.rbRating.rating = moviePreview.moviePreview.rating.toFloat()
            binding.tvMin.text = resources.getString(string.minFormat).format(moviePreview.moviePreview.min)
            binding.cbLike.setOnCheckedChangeListener(null)
            binding.cbLike.isChecked = moviePreview.moviePreview.isLiked
            binding.cbLike.setOnCheckedChangeListener { _, isChecked ->
                moviePreview.moviePreview.isLiked = isChecked
                likeStateChangeListener.invoke(moviePreview.moviePreview.id, isChecked)
                if (isChecked) notifyItemChanged(adapterPosition, MovieItemAnimator.ACTION_FILM_LIKED)
            }
            binding.llLike.setOnClickListener {
                binding.cbLike.isChecked = !moviePreview.moviePreview.isLiked
            }
            binding.root.setOnClickListener { itemClickListener(moviePreview) }
        }

        override val ivLike: View
            get() = binding.ivLike
    }
}