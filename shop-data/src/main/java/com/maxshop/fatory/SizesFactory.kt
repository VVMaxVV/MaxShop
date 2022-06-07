package com.maxshop.fatory

import java.util.Locale
import javax.inject.Inject

class SizesFactory @Inject constructor() {
    fun get(category: String): List<String> {
        return when (category.lowercase(Locale.getDefault())) {
            "electronics" -> emptyList()
            else -> {
                when ((0..4).random()) {
                    0 -> listOf("XS", "S", "M")
                    1 -> listOf("XS", "M", "L", "XL")
                    2 -> listOf("M", "L", "XL")
                    3 -> listOf("XS", "S", "M", "L", "XL")
                    else -> listOf("S", "M", "L")
                }
            }
        }

    }
}