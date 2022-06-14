package com.maxshop.stream

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ColorStream @Inject constructor() : Stream<String> {
    private val _subject = Channel<String>()

    override suspend fun post(data: String) {
        _subject.send(data)
    }

    override fun stream() = _subject.receiveAsFlow()
}
