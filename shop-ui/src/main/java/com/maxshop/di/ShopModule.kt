package com.maxshop.di

import dagger.Module

@Module(
    includes =
    [
        ShopDomainModule::class,
        ShopDataModule::class,
        ShopFragmentModule::class,
        ShopViewModelModule::class
    ]
)
interface ShopModule
