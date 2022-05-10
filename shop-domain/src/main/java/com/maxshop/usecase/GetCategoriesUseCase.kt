package com.maxshop.usecase

import com.maxshop.model.category.Category
import io.reactivex.Single

interface GetCategoriesUseCase {
    fun execute(): Single<List<Category>>
}
