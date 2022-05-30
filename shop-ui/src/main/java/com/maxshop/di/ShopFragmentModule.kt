package com.maxshop.di

import com.maxshop.fragment.CategoriesFragment
import com.maxshop.fragment.ProductsListFragment
import com.maxshop.fragment.SortsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface ShopFragmentModule {
    @ContributesAndroidInjector
    fun provideCategoriesFragment(): CategoriesFragment

    @ContributesAndroidInjector
    fun provideProductListFragment(): ProductsListFragment

    @ContributesAndroidInjector
    fun provideSortsFragment(): SortsFragment
}
