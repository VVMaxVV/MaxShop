package com.maxshop.mapper

import com.maxshop.model.ProductResponse
import com.maxshop.model.product.Product
import javax.inject.Inject

internal class ProductResponseMapper @Inject constructor() {
    fun toDomain(product: ProductResponse): Product {
        return Product(
            product.id.toInt(),
            product.title,
            product.price,
            product.description,
            product.category,
            product.image,
            product.rating.rate,
            product.rating.count
        )
    }
}
