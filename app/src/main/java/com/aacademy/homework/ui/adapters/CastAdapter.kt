package com.aacademy.homework.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.data.local.model.Actor
import com.aacademy.homework.databinding.LayoutCastItemBinding
import com.aacademy.homework.ui.adapters.CastAdapter.CastViewHolder
import com.bumptech.glide.RequestManager

class CastAdapter(val glide: RequestManager) : RecyclerView.Adapter<CastViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var actors: List<Actor>
        get() = differ.currentList
        set(value) = differ.submitList(value)

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
            binding.tvNameCast.text = "${actor.firstName} ${actor.lastName}"
            glide.load(actor.photoPath).into(binding.ivPhotoCast)
        }
    }
}