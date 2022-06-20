package com.maxshop.usecase.impl

import com.maxshop.model.BagProduct
import com.maxshop.repository.BagProductRepository
import com.maxshop.usecase.GetBagProductsUseCase
import javax.inject.Inject

internal class GetBagProductsUseCaseImpl @Inject constructor(
    private val repository: BagProductRepository
) : GetBagProductsUseCase {
    override suspend fun execute(): List<BagProduct> {
        return repository.getAll()
    }
}
