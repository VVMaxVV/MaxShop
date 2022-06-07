package com.maxshop.model.product

data class DetailedProduct(
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: Float,
    val ratingCount: Int,
    val sizesList: List<String>,
    val colorVisibility: Boolean
)
