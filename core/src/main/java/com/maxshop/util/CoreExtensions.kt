package com.maxshop.util

import java.util.IllegalFormatConversionException

fun Number.formatPrice(): String {
    return try {
        String.format("%.2f", this)
    } catch (e: IllegalFormatConversionException) {
        String.format("%.2f", this.toFloat())
    }
}
