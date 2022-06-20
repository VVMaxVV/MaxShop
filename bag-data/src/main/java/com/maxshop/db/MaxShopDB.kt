package com.maxshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxshop.db.dao.BagProductsDao
import com.maxshop.db.entity.BagProductEntity

@Database(entities = [BagProductEntity::class], version = 1)
internal abstract class MaxShopDB : RoomDatabase() {
    abstract fun bagProductsDao(): BagProductsDao
}
