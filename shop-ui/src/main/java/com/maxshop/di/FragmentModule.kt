package com.maxshop.di

import com.maxshop.fragment.CategoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface FragmentModule {
    @ContributesAndroidInjector
    fun provideCategoriesFragment(): CategoriesFragment
}
