package com.maxshop.viewState

import io.reactivex.subjects.PublishSubject

open class BaseViewState {
    sealed class Event {
        data class OnProductClick(val name: String) : Event()
    }

    private val uiEvent = PublishSubject.create<Event>()
    val events = uiEvent.hide()
    fun onProductClick(name: String) {
        uiEvent.onNext(Event.OnProductClick(name))
    }
}
