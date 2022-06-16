package com.maxshop.usecase

import com.maxshop.model.BagProduct

interface GetBagProductsUseCase {
    suspend fun execute(): List<BagProduct>
}
