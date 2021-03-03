package com.joyor.helper

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizantalMidDivider(private val drawable: Drawable?) : RecyclerView.ItemDecoration() {


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val dividerLeft = parent.paddingLeft
        val rightPadding = parent.paddingRight
        val dividerRight = parent.width - rightPadding
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerBottom: Int = child.bottom + params.bottomMargin
            val dividerTop: Int = dividerBottom + (drawable?.intrinsicHeight ?: 0)
            drawable?.setBounds(dividerLeft, dividerBottom, dividerRight, dividerTop)
            drawable?.draw(c)
        }
    }
}