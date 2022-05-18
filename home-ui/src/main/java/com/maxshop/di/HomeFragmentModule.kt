package com.maxshop.di

import com.maxshop.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface HomeFragmentModule {
    @ContributesAndroidInjector
    fun provideHomeFragment(): HomeFragment
}