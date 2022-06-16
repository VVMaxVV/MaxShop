package com.maxshop.usecase

import com.maxshop.model.BagProduct

interface UpdateBagProductUseCase {
    suspend fun execute(product: BagProduct)
}
