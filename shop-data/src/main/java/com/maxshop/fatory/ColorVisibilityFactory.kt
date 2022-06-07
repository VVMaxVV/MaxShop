package com.maxshop.fatory

import com.maxshop.model.ProductResponse
import java.util.Locale
import javax.inject.Inject

internal class ColorVisibilityFactory @Inject constructor() {
    fun get(product: ProductResponse) = when (product.category.lowercase(Locale.getDefault())) {
        "electronics", "jewelery" -> false
        else -> true
    }
}