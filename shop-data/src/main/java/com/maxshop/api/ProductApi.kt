package com.maxshop.api

import com.maxshop.model.ProductResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ProductApi {
    @GET("/products/category/{categoryName}")
    fun getProducts(
        @Path("categoryName") categoryName: String
    ): Single<List<ProductResponse>>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): ProductResponse
}
