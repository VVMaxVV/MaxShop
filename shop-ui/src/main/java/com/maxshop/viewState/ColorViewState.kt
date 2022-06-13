package com.maxshop.viewState

import com.maxshop.adapter.comparator.RecyclerItemComparator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

internal class ColorViewState(
    override val text: String,
    override val isActive: Boolean
) : RecyclerItemComparator, BaseProductOptionsViewState(text, isActive) {

    private val _uiEvent = Channel<Event>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override fun onClick(viewState: BaseProductOptionsViewState) {
        (viewState as? ColorViewState)?.let {
            _uiEvent.trySend(Event.OnClick(it))
        }
    }

    override fun isSameItem(other: Any): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        return text == (other as ColorViewState).text
    }

    override fun isSameContent(other: Any): Boolean {
        other as ColorViewState
        return (text == other.text && isActive == other.isActive)
    }
}
