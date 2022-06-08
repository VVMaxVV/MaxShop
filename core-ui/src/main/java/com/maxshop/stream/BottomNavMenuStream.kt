package com.maxshop.stream

import com.maxshop.utils.eventStream.LiveDataEventStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BottomNavMenuStream @Inject constructor(stream: LiveDataEventStream<Boolean>) {
    private val visibilityStream = stream
    fun postVisibility(visibility: Boolean) {
        visibilityStream.post(visibility)
    }

    fun streamVisibility() = visibilityStream.stream()
}
