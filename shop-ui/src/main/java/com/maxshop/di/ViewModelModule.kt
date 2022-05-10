package com.maxshop.di

import androidx.lifecycle.ViewModel
import com.maxshop.viewModel.CategoriesViewModel
import com.maxshop.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(CategoriesViewModel::class)]
    fun getCategoryViewModel(viewModel: CategoriesViewModel): ViewModel
}
