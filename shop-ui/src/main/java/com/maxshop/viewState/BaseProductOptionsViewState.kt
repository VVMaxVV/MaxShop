package com.maxshop.viewState

internal abstract class BaseProductOptionsViewState(open val text: String, open val isActive: Boolean) {
    sealed class Event {
        data class OnClick(val viewState: BaseProductOptionsViewState) : Event()
    }

    abstract fun onClick(viewState: BaseProductOptionsViewState)
}
