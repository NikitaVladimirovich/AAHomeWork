package com.aacademy.homework.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.databinding.LayoutCastItemBinding
import com.aacademy.homework.ui.moviedetail.CastAdapter.CastViewHolder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CastAdapter(val glide: RequestManager) : RecyclerView.Adapter<CastViewHolder>() {

    var actors: List<Actor> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(LayoutCastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(actor = actors[position])
    }

    override fun getItemCount(): Int = actors.size
    override fun getItemId(position: Int): Long {
        return actors[position].id.toLong()
    }

    inner class CastViewHolder(private val binding: LayoutCastItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(actor: Actor) {
            binding.tvNameCast.text = actor.name
            glide.load(actor.picture)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivPhotoCast)
        }
    }
}
