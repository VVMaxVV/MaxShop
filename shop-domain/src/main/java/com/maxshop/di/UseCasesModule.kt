package com.maxshop.di

import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.usecase.impl.GetCategoriesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UseCasesModule {
    @Binds
    fun getAllCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase
}
