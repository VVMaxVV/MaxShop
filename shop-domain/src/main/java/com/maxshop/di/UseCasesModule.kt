package com.maxshop.di

import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.usecase.GetProductUseCase
import com.maxshop.usecase.GetProductsCategoryUseCase
import com.maxshop.usecase.GetSortsUseCase
import com.maxshop.usecase.impl.GetCategoriesUseCaseImpl
import com.maxshop.usecase.impl.GetDetailedProductUseCaseImpl
import com.maxshop.usecase.impl.GetProductsCategoryUseCaseImpl
import com.maxshop.usecase.impl.GetSortsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UseCasesModule {
    @Binds
    fun provideGetCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    fun provideGetProductsCategoryUseCase(impl: GetProductsCategoryUseCaseImpl): GetProductsCategoryUseCase

    @Binds
    fun provideGetSortsUseCase(impl: GetSortsUseCaseImpl): GetSortsUseCase

    @Binds
    fun provideGetProductUseCase(impl: GetDetailedProductUseCaseImpl): GetProductUseCase
}
