package com.maxshop.repository

import com.maxshop.model.BagProduct

interface BagProductRepository {
    suspend fun getAll(): List<BagProduct>
    suspend fun add(product: BagProduct)
    suspend fun update(product: BagProduct)
    suspend fun delete(product: BagProduct)
}
