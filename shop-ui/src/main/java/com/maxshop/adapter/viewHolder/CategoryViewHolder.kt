package com.maxshop.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.maxshop.shop_ui.databinding.ItemCategoryBinding
import com.maxshop.viewState.CategoryViewState

internal class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        categoryViewState: CategoryViewState
    ) {
        binding.category = categoryViewState
    }
}
