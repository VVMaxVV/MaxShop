package com.maxshop.mapper

import com.maxshop.model.RecyclerItem
import com.maxshop.model.category.Category
import com.maxshop.shop_ui.BR
import com.maxshop.shop_ui.R
import com.maxshop.viewState.CategoryViewState
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toViewState(category: Category) = CategoryViewState(
        category.name,
        category.imageUrl
    )

    fun toRecyclerItem(category: CategoryViewState) = RecyclerItem(
        category,
        R.layout.item_category,
        BR.category
    )
}
