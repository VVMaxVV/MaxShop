package com.maxshop.usecase.impl

import com.maxshop.model.BagProduct
import com.maxshop.repository.BagProductRepository
import com.maxshop.usecase.DeleteProductFromBagUseCase
import javax.inject.Inject

internal class DeleteProductFromBagUseCaseImpl @Inject constructor(
    private val repository: BagProductRepository
) : DeleteProductFromBagUseCase {
    override suspend fun execute(product: BagProduct) {
        repository.delete(product)
    }
}
