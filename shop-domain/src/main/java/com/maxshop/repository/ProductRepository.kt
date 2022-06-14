package com.maxshop.repository

import com.maxshop.model.TypeSort
import com.maxshop.model.product.DetailedProduct
import com.maxshop.model.product.SimplifiedProduct
import io.reactivex.Single

interface ProductRepository {
    fun getSimplifiedProducts(category: String, typeSort: TypeSort): Single<List<SimplifiedProduct>>
    suspend fun getDetailedProduct(id: Int): DetailedProduct
}
