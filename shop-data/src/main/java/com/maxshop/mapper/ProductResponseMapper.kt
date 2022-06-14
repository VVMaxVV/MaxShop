package com.maxshop.mapper

import com.maxshop.fatory.ColorsFactory
import com.maxshop.fatory.SizesFactory
import com.maxshop.model.ProductResponse
import com.maxshop.model.product.DetailedProduct
import com.maxshop.model.product.SimplifiedProduct
import com.maxshop.util.formatPrice
import java.util.Locale
import javax.inject.Inject

internal class ProductResponseMapper @Inject constructor(
    private val sizesFactory: SizesFactory,
    private val colorFactory: ColorsFactory
) {
    fun toSimplifiedProduct(product: ProductResponse) = SimplifiedProduct(
        product.id,
        product.title,
        product.image,
        product.rating.rate,
        product.rating.count,
        product.price.formatPrice()
    )

    fun toDetailedProduct(product: ProductResponse) = DetailedProduct(
        product.id,
        product.title,
        product.price.formatPrice(),
        product.description,
        product.category.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        },
        product.image,
        product.rating.rate,
        product.rating.count,
        sizesFactory.get(product.category),
        colorFactory.get(product.category)
    )
}
