package com.maxshop.usecase

import com.maxshop.model.BagProduct

interface AddProductToBagUseCase {
    suspend fun execute(product: BagProduct)
}
