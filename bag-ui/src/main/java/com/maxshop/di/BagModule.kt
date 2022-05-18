package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        BagFragmentModel::class
    ]
)
interface BagModule
