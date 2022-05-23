package com.maxshop.mapper

import com.maxshop.model.product.Product
import com.maxshop.model.product.SimplifiedProduct
import javax.inject.Inject

class ProductMapper @Inject constructor() {
    fun toSimpleProduct(product: Product): SimplifiedProduct {
        return SimplifiedProduct(
            product.id,
            product.category,
            product.title,
            product.imageUrl,
            product.rating,
            product.ratingCount,
            String.format("%.2f", product.price)
        )
    }
}