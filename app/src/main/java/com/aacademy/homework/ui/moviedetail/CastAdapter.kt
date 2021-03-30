package com.aacademy.homework.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R
import com.aacademy.homework.databinding.LayoutCastItemBinding
import com.aacademy.homework.entities.ActorParcelable
import com.aacademy.homework.extensions.loadImage
import com.aacademy.homework.ui.moviedetail.CastAdapter.CastViewHolder
import com.bumptech.glide.RequestManager

class CastAdapter(val glide: RequestManager) : RecyclerView.Adapter<CastViewHolder>() {

    var actors: List<ActorParcelable> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(LayoutCastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) = holder.bind(actors[position])

    override fun getItemCount(): Int = actors.size

    override fun getItemId(position: Int): Long = actors[position].id

    inner class CastViewHolder(private val binding: LayoutCastItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(actor: ActorParcelable) {
            binding.tvNameCast.text = actor.name
            glide.loadImage(actor.profilePath ?: "")
                .placeholder(R.drawable.actor_photo_placeholder)
                .into(binding.ivPhotoCast)
        }
    }
}
