package com.maxshop.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager


class GridSpaceItemsDecoration(
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        val manager = parent.layoutManager as GridLayoutManager?

        if (position >= manager!!.spanCount) outRect.top = verticalSpacing
        outRect.left = horizontalSpacing
        outRect.right = horizontalSpacing
    }

    companion object {
        @JvmStatic
        fun create(verticalSpacing: Int, horizontalSpacing: Int): GridSpaceItemsDecoration {
            return GridSpaceItemsDecoration(verticalSpacing, horizontalSpacing)
        }
    }
}