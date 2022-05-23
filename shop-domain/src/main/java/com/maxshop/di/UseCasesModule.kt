package com.maxshop.di

import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.usecase.GetProductsCategoryUseCase
import com.maxshop.usecase.impl.GetCategoriesUseCaseImpl
import com.maxshop.usecase.impl.GetProductsCategoryUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UseCasesModule {
    @Binds
    fun provideGetCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    fun provideGetProductsCategoryUseCase(impl: GetProductsCategoryUseCaseImpl): GetProductsCategoryUseCase
}
