package com.maxshop.api

import io.reactivex.Single
import retrofit2.http.GET

internal interface CategoryApi {
    @GET("/products/categories")
    fun getCategoryList(): Single<List<String>>
}
