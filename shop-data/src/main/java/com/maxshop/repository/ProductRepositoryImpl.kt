package com.maxshop.repository

import com.maxshop.api.ProductApi
import com.maxshop.mapper.ProductResponseMapper
import com.maxshop.model.product.Product
import io.reactivex.Single
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productResponseMapper: ProductResponseMapper
) : ProductRepository {
    override fun getProducts(category: String): Single<List<Product>> {
        return productApi.getCategories(category.lowercase()).map { listProductResponse ->
            listProductResponse.map { productResponse ->
                productResponseMapper.toDomain(productResponse)
            }
        }
    }
}