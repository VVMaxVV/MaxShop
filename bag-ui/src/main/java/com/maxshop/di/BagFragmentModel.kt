package com.maxshop.di

import com.maxshop.fragment.BagFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface BagFragmentModel {
    @ContributesAndroidInjector
    fun provideBagFragment(): BagFragment
}
