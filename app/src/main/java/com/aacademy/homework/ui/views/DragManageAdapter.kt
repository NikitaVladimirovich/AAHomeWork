package com.aacademy.homework.ui.views

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class DragManageAdapter(
    private val moveCallback: ((Int, Int) -> Unit)? = null,
    private val swipeCallback: ((ViewHolder, Int) -> Unit)? = null
) : SimpleCallback(
    ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END,
    0
) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        moveCallback?.invoke(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        swipeCallback?.invoke(viewHolder, direction)
    }
}
