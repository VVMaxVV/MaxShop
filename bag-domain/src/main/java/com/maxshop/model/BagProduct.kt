package com.maxshop.model

data class BagProduct(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val color: String?,
    val size: String?,
    var amount: Int,
    val price: String
)
