package com.aacademy.homework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.data.local.model.Actor
import com.bumptech.glide.RequestManager

class CastAdapter(val glide: RequestManager) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder = CastViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.layout_cast_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(actor = actors[position])
    }

    override fun getItemCount(): Int = actors.size

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.tvNameCast)
        private val photo = itemView.findViewById<ImageView>(R.id.ivPhotoCast)

        fun bind(actor: Actor) {
            name.text = "${actor.firstName} ${actor.lastName}"
            glide.load(actor.photoPath).placeholder(R.mipmap.ic_launcher).into(photo)
        }
    }
}