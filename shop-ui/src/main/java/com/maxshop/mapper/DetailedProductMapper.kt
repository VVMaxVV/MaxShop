package com.maxshop.mapper

import com.maxshop.model.BagProduct
import com.maxshop.model.product.DetailedProduct
import javax.inject.Inject

internal class DetailedProductMapper @Inject constructor() {
    fun toBagProduct(
        product: DetailedProduct,
        selectedColor: String?,
        selectedSize: String?
    ): BagProduct {
        product.let {
            return BagProduct(
                it.id,
                it.title,
                it.imageUrl,
                selectedColor,
                selectedSize,
                1,
                it.price
            )
        }
    }
}
