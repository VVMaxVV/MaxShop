package com.maxshop.stream

import kotlinx.coroutines.flow.Flow

interface Stream<T> {
    suspend fun post(data: T)
    fun stream(): Flow<T>
}
