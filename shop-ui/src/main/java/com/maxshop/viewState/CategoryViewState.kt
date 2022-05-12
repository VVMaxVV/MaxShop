package com.maxshop.viewState

import com.maxshop.adapter.comparator.RecyclerItemComparator
import io.reactivex.subjects.PublishSubject

class CategoryViewState(
    val name: String,
    val url: String
) : RecyclerItemComparator {
    sealed class Event {
        data class OnProductClick(val name: String) : Event()
    }

    private val uiEvent = PublishSubject.create<Event>()
    val events = uiEvent.hide()
    fun onProductClick(name: String) {
        uiEvent.onNext(Event.OnProductClick(name))
    }

    override fun isSameItem(other: Any): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        return name == (other as CategoryViewState).name
    }

    override fun isSameContent(other: Any): Boolean {
        other as CategoryViewState
        return (name == other.name && url == other.url)
    }
}
