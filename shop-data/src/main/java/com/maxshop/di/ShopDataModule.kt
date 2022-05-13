package com.maxshop.di

import dagger.Module

@Module(
    includes =
    [
        RepositoryModule::class,
        ApiModule::class
    ]
)
interface ShopDataModule
