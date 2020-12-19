package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.aacademy.homework.databinding.LayoutEmptyBinding

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        LayoutEmptyBinding.inflate(LayoutInflater.from(context), this, true)
    }
}
