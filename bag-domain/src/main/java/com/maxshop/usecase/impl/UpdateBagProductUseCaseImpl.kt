package com.maxshop.usecase.impl

import com.maxshop.model.BagProduct
import com.maxshop.repository.BagProductRepository
import com.maxshop.usecase.UpdateBagProductUseCase
import javax.inject.Inject

internal class UpdateBagProductUseCaseImpl @Inject constructor(
    private val repository: BagProductRepository
) : UpdateBagProductUseCase {
    override suspend fun execute(product: BagProduct) {
        repository.update(product)
    }
}
