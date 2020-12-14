package com.aacademy.homework.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aacademy.homework.R
import com.aacademy.homework.R.styleable
import kotlin.math.max

/**
 * Custom RecyclerView with auto span count computation.
 * Additional attributes can be set via constructor or
 * declared in values\attr.xml and set in layout file.
 *
 * @param minItemWidth The minimum item width in px, default is 0.
 * When this param is 0 - span count sets to 1
 */

class AutofitRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.ratingBarStyle,
    private var minItemWidth: Int = 0,
    private var manager: GridLayoutManager? = null
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        attrs?.let { init(it) }
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, styleable.AutofitRecyclerView).run {
            minItemWidth = getDimensionPixelSize(styleable.AutofitRecyclerView_minItemWidth, 0)
            recycle()
        }
        manager = GridLayoutManager(context, 1)
        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (minItemWidth > 0) {
            val spanCount = max(1, measuredWidth / minItemWidth)
            manager!!.spanCount = spanCount
        }
    }
}
