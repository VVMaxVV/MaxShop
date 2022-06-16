package com.maxshop.mapper

import com.maxshop.db.entity.BagProductEntity
import com.maxshop.model.BagProduct
import com.maxshop.util.formatPrice
import javax.inject.Inject

internal class BagProductEntityMapper @Inject constructor() {
    fun toBagProduct(entity: BagProductEntity): BagProduct {
        entity.let {
            return BagProduct(
                it.id,
                it.title,
                it.imageUrl,
                if (it.color == "") null else it.color,
                if (it.size == "") null else it.size,
                it.amount,
                it.price.formatPrice()
            )
        }
    }

    fun toBagProductEntity(product: BagProduct): BagProductEntity {
        product.let {
            return BagProductEntity(
                it.id,
                it.title,
                it.color ?: "",
                it.size ?: "",
                it.imageUrl,
                it.amount,
                it.price.toFloat()
            )
        }
    }
}
