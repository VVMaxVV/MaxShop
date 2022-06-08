package com.maxshop.fatory

import com.maxshop.const.CategoryConst
import java.util.Locale
import javax.inject.Inject

internal class ColorsFactory @Inject constructor() {
    fun get(category: String): List<String>? {
        return when (category.lowercase(Locale.getDefault())) {
            CategoryConst.ELECTRONICS, CategoryConst.JEWELERY -> null
            else -> {
                when ((0..4).random()) {
                    0 -> listOf("Fuchsia", "Magenta", "Yellow")
                    1 -> listOf("Light Pink", "Lavender", "Fuchsia", "Magenta")
                    2 -> listOf("Pink", "Orchid", "Chartreuse")
                    3 -> listOf("Hot Pink", "Orchid", "Tan", "Slate Gray", "Dark Green")
                    else -> listOf("Cadet Blue", "Medium Blue", "Goldenrod")
                }
            }
        }
    }
}
