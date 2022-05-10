package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        UseCasesModule::class
    ]
)
interface ShopDomainModule
