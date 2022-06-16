package com.maxshop.di

import androidx.lifecycle.ViewModel
import com.maxshop.viewModel.BagViewModel
import com.maxshop.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface BagViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(BagViewModel::class)]
    fun provideBagViewModel(viewModel: BagViewModel): ViewModel
}
