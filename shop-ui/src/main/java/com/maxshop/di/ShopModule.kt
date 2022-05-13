package com.maxshop.di

import dagger.Module

@Module(
    includes =
    [
        ShopDomainModule::class,
        ShopDataModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
interface ShopModule
