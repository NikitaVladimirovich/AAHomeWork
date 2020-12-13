package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.core.view.children
import com.aacademy.homework.R.attr

class FitSystemWindowsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = attr.ratingBarStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        children.iterator().forEach {
            it.dispatchApplyWindowInsets(insets)
        }
        return insets
    }
}
