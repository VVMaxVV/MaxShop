package com.maxshop.repository

import com.maxshop.api.CategoryApi
import com.maxshop.fatory.CategoryFactory
import com.maxshop.model.category.Category
import io.reactivex.Single
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val categoryFactory: CategoryFactory
) : CategoryRepository {
    override fun getAllCategories(): Single<List<Category>> {
        return categoryApi.getCategories().map {
            it.map {
                categoryFactory.get(it)
            }
        }
    }
}
