package com.maxshop.di

import com.maxshop.repository.BagProductRepository
import com.maxshop.repository.BagProductRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface BagRepositoryModule {
    @Binds
    fun provideBagProductRepository(impl: BagProductRepositoryImpl): BagProductRepository
}
