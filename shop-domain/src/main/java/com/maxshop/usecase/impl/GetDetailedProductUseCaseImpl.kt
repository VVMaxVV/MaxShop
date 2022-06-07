package com.maxshop.usecase.impl

import com.maxshop.model.product.DetailedProduct
import com.maxshop.repository.ProductRepository
import com.maxshop.usecase.GetProductUseCase
import javax.inject.Inject

internal class GetDetailedProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductUseCase {
    override suspend fun execute(id: Int): DetailedProduct {
        return productRepository.getDetailedProduct(id)
    }
}