package com.maxshop.usecase

import com.maxshop.model.product.SimplifiedProduct
import io.reactivex.Single

interface GetProductsCategoryUseCase {
    fun execute(category: String): Single<List<SimplifiedProduct>>
}