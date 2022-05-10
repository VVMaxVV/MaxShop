package com.maxshop.response.mapper

import android.util.Log
import com.maxshop.exception.NoSuchCategoryException
import com.maxshop.fatory.CategoryFactory
import com.maxshop.model.category.Category
import com.maxshop.response.CategoryResponse
import javax.inject.Inject

internal class CategoryResponseMapper @Inject constructor(
    private val categoryFactory: CategoryFactory
) {
    fun toDomain(category: CategoryResponse): Category? {
        return try {
            categoryFactory.get(category.categoryName)
        } catch (e: NoSuchCategoryException) {
            Log.e(CategoryResponseMapper::class.java.name, e.message ?: "Invalid category")
            null
        }
    }
}
