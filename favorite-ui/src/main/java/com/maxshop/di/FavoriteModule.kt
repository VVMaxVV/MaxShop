package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        FavoriteFragmentModule::class
    ]
)
interface FavoriteModule

