package com.maxshop.di

import com.maxshop.repository.CategoryRepository
import com.maxshop.repository.CategoryRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {
    @Binds
    fun getCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}
