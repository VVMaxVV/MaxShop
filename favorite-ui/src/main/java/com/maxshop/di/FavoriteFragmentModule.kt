package com.maxshop.di

import com.maxshop.fragment.FavoriteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface FavoriteFragmentModule {
    @ContributesAndroidInjector
    fun provideFavoriteFragment(): FavoriteFragment
}