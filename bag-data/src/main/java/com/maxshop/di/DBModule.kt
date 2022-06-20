package com.maxshop.di

import android.content.Context
import androidx.room.Room
import com.maxshop.db.MaxShopDB
import com.maxshop.db.dao.BagProductsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DBModule {
    @Provides
    @Singleton
    fun getDB(applicationContext: Context): MaxShopDB {
        return Room.databaseBuilder(
            applicationContext,
            MaxShopDB::class.java,
            "ApplicationDB"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun getCartProductDao(maxShopDB: MaxShopDB): BagProductsDao {
        return maxShopDB.bagProductsDao()
    }
}
