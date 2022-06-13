package com.maxshop.model.product

data class SimplifiedProduct(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val ratingCount: Int,
    val price: String,
)
