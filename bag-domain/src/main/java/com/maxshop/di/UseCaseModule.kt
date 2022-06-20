package com.maxshop.di

import com.maxshop.usecase.AddProductToBagUseCase
import com.maxshop.usecase.DeleteProductFromBagUseCase
import com.maxshop.usecase.GetBagProductsUseCase
import com.maxshop.usecase.UpdateBagProductUseCase
import com.maxshop.usecase.impl.AddProductToBagUseCaseImpl
import com.maxshop.usecase.impl.DeleteProductFromBagUseCaseImpl
import com.maxshop.usecase.impl.GetBagProductsUseCaseImpl
import com.maxshop.usecase.impl.UpdateBagProductUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UseCaseModule {
    @Binds
    fun provideGetBagProductsUseCase(impl: GetBagProductsUseCaseImpl): GetBagProductsUseCase

    @Binds
    fun provideAddProductToBagUseCase(impl: AddProductToBagUseCaseImpl): AddProductToBagUseCase

    @Binds
    fun provideUpdateBagProductUseCase(impl: UpdateBagProductUseCaseImpl): UpdateBagProductUseCase

    @Binds
    fun provideDeleteProductFromBagUseCase(impl: DeleteProductFromBagUseCaseImpl): DeleteProductFromBagUseCase
}
