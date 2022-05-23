package com.maxshop.di

import com.maxshop.fragment.CategoriesFragment
import com.maxshop.fragment.ProductsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface ShopFragmentModule {
    @ContributesAndroidInjector
    fun provideCategoriesFragment(): CategoriesFragment

    @ContributesAndroidInjector
    fun provideProductListFragment(): ProductsListFragment
}
