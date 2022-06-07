package com.maxshop.stream

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class SizeStream @Inject constructor() {
    private val _subject = MutableSharedFlow<String>()
    val subject = _subject.asSharedFlow()

    suspend fun post(size: String) {
        _subject.emit(size)
    }
}