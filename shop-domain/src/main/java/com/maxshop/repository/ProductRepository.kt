package com.maxshop.repository

import com.maxshop.model.TypeSort
import com.maxshop.model.product.Product
import io.reactivex.Single

interface ProductRepository {
    fun getProducts(category: String, typeSort: TypeSort): Single<List<Product>>
}
