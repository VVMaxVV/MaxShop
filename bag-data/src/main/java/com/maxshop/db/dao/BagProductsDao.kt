package com.maxshop.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.maxshop.db.entity.BagProductEntity

@Dao
internal interface BagProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(product: BagProductEntity)

    @Query("SELECT * FROM BAG")
    suspend fun getAll(): List<BagProductEntity>

    @Update
    suspend fun update(product: BagProductEntity)

    @Delete
    suspend fun delete(product: BagProductEntity)
}
