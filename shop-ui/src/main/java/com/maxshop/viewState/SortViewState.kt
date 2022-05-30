package com.maxshop.viewState

import androidx.lifecycle.MutableLiveData
import com.maxshop.adapter.comparator.RecyclerItemComparator
import com.maxshop.model.TypeSort
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

internal class SortViewState(
    val position: Int,
    val type: TypeSort,
    val isActive: MutableLiveData<Boolean>
) : RecyclerItemComparator {
    sealed class Event {
        data class OnClick(val viewState: SortViewState) : Event()
    }

    private val uiEvent = PublishSubject.create<Event>()
    val events: Observable<Event> = uiEvent.hide()

    fun onClick(viewState: SortViewState) {
        uiEvent.onNext(Event.OnClick(viewState))
    }

    override fun isSameItem(other: Any): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        return type == (other as SortViewState).type
    }

    override fun isSameContent(other: Any): Boolean {
        other as SortViewState
        return (this.type == other.type && this.isActive.value == other.isActive.value)
    }
}
