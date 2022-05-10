package com.maxshop.di

import com.maxshop.api.CategoryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class ApiModule {
    @Provides
    fun getCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }
}
