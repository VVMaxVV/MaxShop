package com.maxshop.adapter.comparator

interface RecyclerItemComparator {
    fun isSameItem(other: Any): Boolean
    fun isSameContent(other: Any): Boolean
}
