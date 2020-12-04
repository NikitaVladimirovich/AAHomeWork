package com.aacademy.homework.ui.movielist

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ALPHA
import androidx.recyclerview.widget.RecyclerView.SCALE_X
import androidx.recyclerview.widget.RecyclerView.SCALE_Y
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.google.android.material.animation.AnimationUtils.DECELERATE_INTERPOLATOR

class MovieItemAnimator : DefaultItemAnimator() {

    companion object {

        val ACTION_FILM_LIKED = "ACTION_FILM_LIKED"
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
            if (ACTION_FILM_LIKED == preInfo.updateAction) {
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
            PropertyValuesHolder.ofFloat(SCALE_X, 0.0f, 2.0f),
            PropertyValuesHolder.ofFloat(SCALE_Y, 0.0f, 2.0f),
            PropertyValuesHolder.ofFloat(ALPHA, 0.0f, 1.0f, 0.0f)
        )
        scaleLikeIcon.interpolator = DECELERATE_INTERPOLATOR
        scaleLikeIcon.duration = 400
        val scaleLikeBackground: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView, PropertyValuesHolder.ofFloat(SCALE_X, 1.0f, 0.95f, 1.0f),
            PropertyValuesHolder.ofFloat(SCALE_Y, 1.0f, 0.95f, 1.0f)
        )
        scaleLikeBackground.interpolator = AccelerateDecelerateInterpolator()
        scaleLikeBackground.duration = 400
        animatorSet.playTogether(scaleLikeIcon, scaleLikeBackground)
        animatorSet.start()
    }

    override fun animateRemove(holder: ViewHolder): Boolean {
        val animatorSet = AnimatorSet()
        val startY = holder.itemView.y
        val scaleBackground: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView, PropertyValuesHolder.ofFloat("y", startY, holder.itemView.y + 150.0f)
        )
        scaleBackground.interpolator = DECELERATE_INTERPOLATOR
        scaleBackground.duration = 600
        val fadeBackground: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView, PropertyValuesHolder.ofFloat(ALPHA, 1.0f, 0.0f)
        )
        fadeBackground.interpolator = DECELERATE_INTERPOLATOR
        fadeBackground.duration = 600

        animatorSet.addListener(object : AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                dispatchRemoveStarting(holder)
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animator: Animator) {
                dispatchRemoveFinished(holder)
                holder.itemView.y = startY
            }

            override fun onAnimationCancel(animation: Animator?) {
            }
        })
        animatorSet.playTogether(scaleBackground, fadeBackground)
        animatorSet.start()
        return true
    }

    override fun onRemoveStarting(item: ViewHolder?) {
        super.onRemoveStarting(item)
        item?.itemView?.isEnabled = false
    }

    override fun onAddFinished(item: ViewHolder?) {
        super.onAddFinished(item)
        item?.itemView?.isEnabled = true
    }

    override fun animateAdd(holder: ViewHolder): Boolean {
        val animatorSet = AnimatorSet()
        val scaleBackground: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView, PropertyValuesHolder.ofFloat("y", holder.itemView.y + 150.0f, holder.itemView.y)
        )
        scaleBackground.interpolator = DECELERATE_INTERPOLATOR
        scaleBackground.duration = 600
        val fadeBackground: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView, PropertyValuesHolder.ofFloat(ALPHA, 0.0f, 1.0f)
        )
        fadeBackground.interpolator = DECELERATE_INTERPOLATOR
        fadeBackground.duration = 600
        animatorSet.addListener(object : AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                dispatchAddStarting(holder)
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animator: Animator) {
                dispatchAddFinished(holder)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }
        })
        animatorSet.playTogether(scaleBackground, fadeBackground)
        animatorSet.start()
        return true
    }

    class CharacterItemHolderInfo(var updateAction: String?) : ItemHolderInfo()

    abstract class LikeViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        abstract val ivLike: View
    }
}