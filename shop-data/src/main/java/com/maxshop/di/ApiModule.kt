package com.maxshop.di

import com.maxshop.api.CategoryApi
import com.maxshop.api.ProductApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class ApiModule {
    @Provides
    fun getCategoryApi(
        retrofit: Retrofit
    ): CategoryApi = retrofit.create(CategoryApi::class.java)

    @Provides
    fun getProductApi(
        retrofit: Retrofit
    ): ProductApi = retrofit.create(ProductApi::class.java)
}
