package com.maxshop.di

import com.maxshop.fragment.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface ProfileFragmentModule {
    @ContributesAndroidInjector
    fun provideProfileFragment(): ProfileFragment
}