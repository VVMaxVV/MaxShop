package com.maxshop.repository

import com.maxshop.api.ProductApi
import com.maxshop.mapper.ProductResponseMapper
import com.maxshop.model.TypeSort
import com.maxshop.model.product.DetailedProduct
import com.maxshop.model.product.SimplifiedProduct
import io.reactivex.Single
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productResponseMapper: ProductResponseMapper
) : ProductRepository {
    override fun getSimplifiedProducts(
        category: String,
        typeSort: TypeSort
    ): Single<List<SimplifiedProduct>> {
        return productApi.getProducts(category.lowercase()).map { listProductResponse ->
            listProductResponse.map { productResponse ->
                productResponseMapper.toSimplifiedProduct(productResponse)
            }.toMutableList()
                .also {
                    when (typeSort) {
                        TypeSort.Popular -> {
                            it.sortedByDescending { it.id }
                        }
                        TypeSort.PriceHighestToLow -> {
                            it.sortByDescending { it.price.toFloat() }
                        }
                        TypeSort.PriceLowestToHigh -> {
                            it.sortBy { it.price.toFloat() }
                        }
                    }
                }
        }
    }

    override suspend fun getDetailedProduct(id: Int): DetailedProduct {
        return productResponseMapper.toDetailedProduct(productApi.getProduct(id))
    }

    override suspend fun getDetailedProduct(id: Int): DetailedProduct {
        return productResponseMapper.toDetailedProduct(productApi.getProduct(id))
    }
}
