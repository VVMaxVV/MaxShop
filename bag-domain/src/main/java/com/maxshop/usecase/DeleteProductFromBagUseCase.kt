package com.maxshop.usecase

import com.maxshop.model.BagProduct

interface DeleteProductFromBagUseCase {
    suspend fun execute(product: BagProduct)
}
