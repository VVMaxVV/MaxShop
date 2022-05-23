package com.maxshop.repository

import com.maxshop.model.product.Product
import io.reactivex.Single

interface ProductRepository {
    fun getProducts(category: String): Single<List<Product>>
}