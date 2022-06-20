package com.maxshop.repository

import com.maxshop.db.dao.BagProductsDao
import com.maxshop.mapper.BagProductEntityMapper
import com.maxshop.model.BagProduct
import javax.inject.Inject

internal class BagProductRepositoryImpl @Inject constructor(
    private val bagProductsDao: BagProductsDao,
    private val productEntityMapper: BagProductEntityMapper
) : BagProductRepository {
    override suspend fun getAll(): List<BagProduct> {
        return bagProductsDao.getAll().map {
            productEntityMapper.toBagProduct(it)
        }
    }

    override suspend fun add(product: BagProduct) {
        bagProductsDao.add(productEntityMapper.toBagProductEntity(product))
    }

    override suspend fun update(product: BagProduct) {
        bagProductsDao.update(productEntityMapper.toBagProductEntity(product))
    }

    override suspend fun delete(product: BagProduct) {
        bagProductsDao.delete(productEntityMapper.toBagProductEntity(product))
    }
}
