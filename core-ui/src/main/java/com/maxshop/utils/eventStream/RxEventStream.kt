package com.maxshop.utils.eventStream

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RxEventStream<T> @Inject constructor() {
    private val _subject: BehaviorSubject<T> = BehaviorSubject.create()

    fun post(data: T) {
        _subject.onNext(data!!)
    }

    fun stream(): Observable<T> = _subject.hide()
}
