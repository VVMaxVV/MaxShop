package com.maxshop.usecase.impl

import com.maxshop.mapper.ProductMapper
import com.maxshop.model.TypeSort
import com.maxshop.model.product.SimplifiedProduct
import com.maxshop.repository.ProductRepository
import com.maxshop.usecase.GetProductsCategoryUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetProductsCategoryUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) : GetProductsCategoryUseCase {
    override fun execute(category: String, typeSort: TypeSort): Single<List<SimplifiedProduct>> {
        return productRepository.getProducts(category, typeSort).map { productList ->
            productList.map {
                productMapper.toSimpleProduct(it)
            }
        }
    }
}
