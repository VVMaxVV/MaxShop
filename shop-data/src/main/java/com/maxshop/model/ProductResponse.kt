package com.maxshop.model

internal data class ProductResponse(
    val id: String,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingResponse
)
