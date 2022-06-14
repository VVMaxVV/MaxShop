package com.maxshop.stream

import com.maxshop.utils.eventStream.LiveDataEventStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BottomNavMenuStream @Inject constructor(stream: LiveDataEventStream<Boolean>) {
    private val visibilityStream = stream
    fun post(visibility: Boolean) {
        visibilityStream.post(visibility)
    }

    fun stream() = visibilityStream.stream()
}
