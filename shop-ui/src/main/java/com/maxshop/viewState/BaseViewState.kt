package com.maxshop.viewState

import io.reactivex.subjects.PublishSubject

open class BaseViewState {
    var type : Int? = null
    var id: Long? =  null

    sealed class Event {
        data class OnProductClick(val name: String) : Event()
    }

    private val uiEvent = PublishSubject.create<Event>()
    val events = uiEvent.hide()
    fun onProductClick(name: String) {
        uiEvent.onNext(Event.OnProductClick(name))
    }
}
