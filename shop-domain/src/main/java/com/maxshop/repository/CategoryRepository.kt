package com.maxshop.repository

import com.maxshop.model.category.Category
import io.reactivex.Single

interface CategoryRepository {
    fun getAllCategories(): Single<List<Category>>
}
