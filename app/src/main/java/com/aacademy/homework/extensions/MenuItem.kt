package com.aacademy.homework.extensions

import android.view.MenuItem
import com.aacademy.homework.foundations.SafeClickListener

fun MenuItem.setSafeOnClickListener(onSafeClick: () -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick()
    }
    setOnMenuItemClickListener(safeClickListener)
}
