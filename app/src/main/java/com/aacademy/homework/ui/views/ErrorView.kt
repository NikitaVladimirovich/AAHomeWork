package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.aacademy.homework.databinding.LayoutErrorBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = LayoutErrorBinding.inflate(LayoutInflater.from(context), this, true)
    var reloadListener: (() -> Unit)? = null

    init {
        binding.reload.setOnClickListener { reloadListener?.invoke() }
    }
}
