package com.maxshop.fatory

import com.maxshop.model.ProductResponse
import java.util.Locale
import javax.inject.Inject

internal class SizeVisibilityFactory @Inject constructor() {
    fun get(product: ProductResponse) = when (product.category.lowercase(Locale.getDefault())) {
        "electronics" -> false
        else -> true
    }
}
