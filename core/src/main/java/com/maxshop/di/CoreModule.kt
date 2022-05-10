package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        RetrofitModule::class
    ]
)
interface CoreModule
