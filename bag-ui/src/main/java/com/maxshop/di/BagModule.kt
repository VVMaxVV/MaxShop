package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        BagFragmentModule::class
    ]
)
interface BagModule
