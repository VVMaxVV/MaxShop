package com.maxshop.model.product

data class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: Float,
    val ratingCount: Int
)
