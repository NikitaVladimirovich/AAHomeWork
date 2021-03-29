package com.aacademy.homework.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import com.aacademy.homework.foundations.SafeClickListener

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

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setSafeOnClickListener(onSafeClick: () -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick()
    }
    setOnClickListener(safeClickListener)
}
