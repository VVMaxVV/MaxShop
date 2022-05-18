package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        HomeFragmentModule::class
    ]
)
interface HomeModule
