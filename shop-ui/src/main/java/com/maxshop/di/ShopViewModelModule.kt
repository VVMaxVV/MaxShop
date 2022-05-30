package com.maxshop.di

import androidx.lifecycle.ViewModel
import com.maxshop.viewModel.CategoriesViewModel
import com.maxshop.viewModel.ProductsListViewModel
import com.maxshop.viewModel.SortsViewModel
import com.maxshop.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ShopViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(CategoriesViewModel::class)]
    fun provideCategoryViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ProductsListViewModel::class)]
    fun provideProductListViewModel(viewModel: ProductsListViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SortsViewModel::class)]
    fun provideSortViewModel(viewModel: SortsViewModel): ViewModel
}
