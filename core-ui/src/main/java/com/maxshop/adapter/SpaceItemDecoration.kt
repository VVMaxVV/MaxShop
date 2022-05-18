package com.maxshop.adapter

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    @param:Dimension val left: Int = 0,
    @param:Dimension val top: Int = 0,
    @param:Dimension val right: Int = 0,
    @param:Dimension val bottom: Int = 0,
    @param:androidx.annotation.IntRange(from = 0) private val skipTop: Int = 0,
    @param:androidx.annotation.IntRange(from = 0) private val skipBottom: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        if (position >= skipTop && position < itemCount - skipBottom) {
            outRect.set(left, top, right, bottom)
        }
    }

    companion object {
        @JvmStatic
        fun create(
            @Dimension left: Int = 0,
            @Dimension top: Int = 0,
            @Dimension right: Int = 0,
            @Dimension bottom: Int = 0,
            @androidx.annotation.IntRange(from = 0) skipTop: Int = 0,
            @androidx.annotation.IntRange(from = 0) skipBottom: Int = 0
        ): SpaceItemDecoration {
            return SpaceItemDecoration(left, top, right, bottom, skipTop, skipBottom)
        }
    }
}
