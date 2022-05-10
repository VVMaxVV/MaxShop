package com.maxshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxshop.adapter.viewHolder.CategoryViewHolder
import com.maxshop.shop_ui.databinding.ItemCategoryBinding
import com.maxshop.viewState.BaseViewState
import com.maxshop.viewState.CategoryViewState
import javax.inject.Inject

internal class CategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categoryList = mutableListOf<BaseViewState>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater
                    .from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun addData(categoriesLst: List<BaseViewState>) {
        categoryList.clear()
        categoryList.addAll(categoriesLst)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(
            categoryList[position] as CategoryViewState
        )
    }
}
