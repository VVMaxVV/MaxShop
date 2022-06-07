package com.maxshop.usecase

import com.maxshop.model.product.DetailedProduct

interface GetProductUseCase {
    suspend fun execute(id: Int): DetailedProduct
}