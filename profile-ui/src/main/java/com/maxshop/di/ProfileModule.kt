package com.maxshop.di

import dagger.Module

@Module(
    includes = [
        ProfileFragmentModule::class
    ]
)
interface ProfileModule
