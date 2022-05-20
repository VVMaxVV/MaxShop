package com.maxshop.di

import dagger.Module

@Module(
    includes =
    [
        ShopDomainModule::class,
        ShopDataModule::class,
        ShopFragmentModule::class,
        ViewModelModule::class
    ]
)
interface ShopModule
