package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        BagFragmentModule::class,
        BagViewModelModule::class,
        BagDomainModule::class,
        BagDataModule::class
    ]
)
interface BagModule
