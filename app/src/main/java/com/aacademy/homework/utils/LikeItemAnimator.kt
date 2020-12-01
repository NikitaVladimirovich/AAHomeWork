package com.aacademy.homework.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.google.android.material.animation.AnimationUtils.DECELERATE_INTERPOLATOR

class LikeItemAnimator : DefaultItemAnimator() {

    companion object {

        val ACTION_LIKE_IMAGE_DOUBLE_CLICKED = "ACTION_LIKE_IMAGE_DOUBLE_CLICKED"
    }

    override fun canReuseUpdatedViewHolder(viewHolder: ViewHolder): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(
        state: State,
        viewHolder: ViewHolder,
        changeFlags: Int,
        payloads: List<Any?>
    ): ItemHolderInfo {
        if (changeFlags == FLAG_CHANGED) {
            for (payload in payloads) {
                if (payload is String) {
                    return CharacterItemHolderInfo(payload as String?)
                }
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun animateChange(
        oldHolder: ViewHolder,
        newHolder: ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        if (preInfo is CharacterItemHolderInfo) {
            val holder = newHolder as LikeViewHolder
            if (ACTION_LIKE_IMAGE_DOUBLE_CLICKED == preInfo.updateAction) {
                animatePhotoLike(holder)
            }
        }
        return false
    }

    private fun animatePhotoLike(holder: LikeViewHolder) {
        holder.ivLike.visibility = View.VISIBLE
        holder.ivLike.scaleY = 0.0f
        holder.ivLike.scaleX = 0.0f
        val animatorSet = AnimatorSet()
        val scaleLikeIcon: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.ivLike,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 2.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 2.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f, 0.0f)
        )
        scaleLikeIcon.interpolator = DECELERATE_INTERPOLATOR
        scaleLikeIcon.duration = 1000
        val scaleLikeBackground: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView, PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.95f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.95f, 1.0f)
        )
        scaleLikeBackground.interpolator = DECELERATE_INTERPOLATOR
        scaleLikeBackground.duration = 600
        animatorSet.playTogether(scaleLikeIcon, scaleLikeBackground)
        animatorSet.start()
    }

    class CharacterItemHolderInfo(var updateAction: String?) : ItemHolderInfo()

    abstract class LikeViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        abstract val ivLike: View
    }
}