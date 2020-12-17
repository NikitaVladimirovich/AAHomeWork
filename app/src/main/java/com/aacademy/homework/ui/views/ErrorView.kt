package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.aacademy.homework.databinding.LayoutErrorBinding

class ErrorView : RelativeLayout {

    private val binding = LayoutErrorBinding.inflate(LayoutInflater.from(context), this, true)
    var reloadListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        binding.reload.setOnClickListener { reloadListener?.invoke() }
    }
}
