package com.aacademy.homework.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator

fun View.startCircularReveal(posX: Int, posY: Int, radius: Float) {
    addOnLayoutChangeListener(
        object : OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                v.removeOnLayoutChangeListener(this)
                val anim = ViewAnimationUtils.createCircularReveal(v, posX, posY, 0f, radius)
                anim.duration = 1000
                anim.interpolator = AccelerateDecelerateInterpolator()
                anim.addListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            v.isClickable = true
                        }

                        override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                            super.onAnimationStart(animation, isReverse)
                            v.isClickable = false
                        }
                    }
                )
                anim.start()
            }
        }
    )
}
