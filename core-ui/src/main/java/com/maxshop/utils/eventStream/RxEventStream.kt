package com.maxshop.utils.eventStream

import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RxEventStream<T> @Inject constructor() {
    private val _subject: PublishSubject<T> = PublishSubject.create()

    fun post(data: T) {
        _subject.onNext(data!!)
    }

    fun stream() = _subject.hide()
}
