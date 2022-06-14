package com.maxshop.usecase.impl

import com.maxshop.model.TypeSort
import com.maxshop.model.product.SimplifiedProduct
import com.maxshop.repository.ProductRepository
import com.maxshop.usecase.GetProductsCategoryUseCase
import io.reactivex.Single
import javax.inject.Inject

internal class GetProductsCategoryUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductsCategoryUseCase {
    override fun execute(category: String, typeSort: TypeSort): Single<List<SimplifiedProduct>> {
        return productRepository.getSimplifiedProducts(category, typeSort)
    }
}
