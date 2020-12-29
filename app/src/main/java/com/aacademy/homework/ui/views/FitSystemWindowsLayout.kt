package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.core.view.children

class FitSystemWindowsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        children.iterator().forEach {
            it.dispatchApplyWindowInsets(insets)
        }
        return insets
    }
}
