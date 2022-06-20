package com.maxshop.usecase.impl

import com.maxshop.model.BagProduct
import com.maxshop.repository.BagProductRepository
import com.maxshop.usecase.AddProductToBagUseCase
import javax.inject.Inject

internal class AddProductToBagUseCaseImpl @Inject constructor(
    private val repository: BagProductRepository
) : AddProductToBagUseCase {
    override suspend fun execute(product: BagProduct) {
        repository.add(product)
    }
}
