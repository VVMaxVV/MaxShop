package com.maxshop.mapper

import com.maxshop.model.category.Category
import com.maxshop.viewState.BaseViewState
import com.maxshop.viewState.CategoryViewState
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toViewState(category: Category): BaseViewState {
        return CategoryViewState(
            category.categoryName,
            category.imageUrl
        )
    }
}
