package com.joyor.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joyor.R

class HorizontalDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val padding = parent.context.resources.getDimension(R.dimen._6sdp).toInt()

        when (itemPosition) {
            0 -> {
                outRect.left = padding
            }
            state.itemCount - 1 -> {
                outRect.right = padding
            }
            else -> {
                outRect.left = padding / 3
                outRect.right = padding / 3
            }
        }
        outRect.top = padding / 2
        outRect.bottom = padding / 2
    }
}