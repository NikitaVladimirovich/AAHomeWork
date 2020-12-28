package com.aacademy.homework.extensions

import androidx.fragment.app.Fragment
import com.aacademy.homework.ui.activities.MainActivity

fun Fragment.showLoading() {
    (activity as MainActivity).let {
        it.showLoading()
    }
}

fun Fragment.hideLoading() {
    (activity as MainActivity).let {
        it.hideLoading()
    }
}
