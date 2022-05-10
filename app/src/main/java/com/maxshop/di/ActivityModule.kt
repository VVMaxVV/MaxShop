package com.maxshop.di

import com.maxshop.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun mainActivity(): MainActivity
}