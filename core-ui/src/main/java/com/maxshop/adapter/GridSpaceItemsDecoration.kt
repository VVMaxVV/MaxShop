package com.maxshop.adapter

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemsDecoration(
    @param:Dimension val verticalSpacing: Int,
    @param:Dimension val horizontalSpacing: Int
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

        (parent.layoutManager as? GridLayoutManager)?.let {
            if (position >= it.spanCount) outRect.top = verticalSpacing
        }

        outRect.left = horizontalSpacing
        outRect.right = horizontalSpacing
    }

    companion object {
        @JvmStatic
        fun create(
            @Dimension verticalSpacing: Int,
            @Dimension horizontalSpacing: Int
        ): GridSpaceItemsDecoration {
            return GridSpaceItemsDecoration(verticalSpacing, horizontalSpacing)
        }
    }
}
