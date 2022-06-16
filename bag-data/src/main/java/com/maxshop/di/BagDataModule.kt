package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        DBModule::class,
        BagRepositoryModule::class
    ]
)
interface BagDataModule
