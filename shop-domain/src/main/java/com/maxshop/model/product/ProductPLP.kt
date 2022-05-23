package com.maxshop.model.product

data class ProductPLP(
    val id: UInt,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val ratingCount: Int,
    val price: String
)