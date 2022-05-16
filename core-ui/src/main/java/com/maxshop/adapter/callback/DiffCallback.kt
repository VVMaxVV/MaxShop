package com.maxshop.adapter.callback

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.maxshop.adapter.comparator.RecyclerItemComparator
import com.maxshop.model.RecyclerItem

internal class DiffCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.data
        val newData = newItem.data
        return if (oldData is RecyclerItemComparator && newData is RecyclerItemComparator
        ) {
            oldData.isSameItem(newData)
        } else oldData == newData
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.data
        val newData = newItem.data
        return if (oldData is RecyclerItemComparator && newData is RecyclerItemComparator
        ) {
            oldData.isSameContent(newData)
        } else oldData == newData
    }
}
