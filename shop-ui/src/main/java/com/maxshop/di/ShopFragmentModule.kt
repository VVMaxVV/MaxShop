package com.maxshop.di

import com.maxshop.fragment.CategoriesFragment
import com.maxshop.fragment.ColorSelectionFragment
import com.maxshop.fragment.ProductDetailsFragment
import com.maxshop.fragment.ProductsListFragment
import com.maxshop.fragment.SizeSelectionFragment
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

    @ContributesAndroidInjector
    fun provideProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    fun provideSizeSelectionFragment(): SizeSelectionFragment

    @ContributesAndroidInjector
    fun provideColorSelectionFragment(): ColorSelectionFragment
}
