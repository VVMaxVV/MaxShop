package com.maxshop.viewState

import android.util.Log
import com.maxshop.adapter.comparator.RecyclerItemComparator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class SizeViewState(
    val text: String,
    val isActive: Boolean
) : RecyclerItemComparator {
    sealed class Event {
        data class OnClick(val sizeViewState: SizeViewState) : Event()
    }

    private val uiEvent = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val events = uiEvent.asSharedFlow()

    fun onClick(sizeViewState: SizeViewState) {
        uiEvent.tryEmit(Event.OnClick(sizeViewState))
    }

    override fun isSameItem(other: Any): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        return text == (other as SizeViewState).text
    }

    override fun isSameContent(other: Any): Boolean {
        other as SizeViewState
        return (text == other.text && isActive == other.isActive)
    }
}