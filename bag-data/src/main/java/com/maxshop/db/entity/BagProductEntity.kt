package com.maxshop.db.entity

import androidx.room.Entity

@Entity(tableName = "Bag", primaryKeys = ["id", "size", "color"])
internal data class BagProductEntity(
    val id: Int,
    val title: String,
    val color: String,
    val size: String,
    val imageUrl: String,
    val amount: Int,
    val price: Float,
)
