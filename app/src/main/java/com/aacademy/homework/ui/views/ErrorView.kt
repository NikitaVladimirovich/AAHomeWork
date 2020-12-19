package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.aacademy.homework.databinding.LayoutErrorBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding = LayoutErrorBinding.inflate(LayoutInflater.from(context), this, true)
    var reloadListener: (() -> Unit)? = null

    init {
        binding.reload.setOnClickListener { reloadListener?.invoke() }
    }
}
