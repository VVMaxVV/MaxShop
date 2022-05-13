package com.maxshop.usecase.impl

import com.maxshop.model.category.Category
import com.maxshop.repository.CategoryRepository
import com.maxshop.usecase.GetCategoriesUseCase
import io.reactivex.Single
import javax.inject.Inject

internal class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : GetCategoriesUseCase {
    override fun execute(): Single<List<Category>> {
        return categoryRepository.getAllCategories()
    }
}
