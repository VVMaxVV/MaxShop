package com.maxshop.di

import com.maxshop.repository.CategoryRepository
import com.maxshop.repository.CategoryRepositoryImpl
import com.maxshop.repository.ProductRepository
import com.maxshop.repository.ProductRepositoryImpl
import com.maxshop.repository.ToolsRepository
import com.maxshop.repository.ToolsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {
    @Binds
    fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun provideProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun provideToolsRepository(impl: ToolsRepositoryImpl): ToolsRepository
}
