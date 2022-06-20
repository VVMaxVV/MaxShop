package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        UseCaseModule::class
    ]
)
interface BagDomainModule
