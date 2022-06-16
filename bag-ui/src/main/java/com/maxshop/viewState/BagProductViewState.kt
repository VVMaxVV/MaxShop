package com.maxshop.viewState

import androidx.lifecycle.MutableLiveData
import com.maxshop.adapter.comparator.RecyclerItemComparator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

internal class BagProductViewState(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val color: String?,
    val size: String?,
    val amount: MutableLiveData<Int>,
    val price: String
) :
    RecyclerItemComparator {
    sealed class Event {
        data class OnClick(val id: Int) : Event()
        data class UpdateProduct(val viewState: BagProductViewState) : Event()
        data class DeleteProduct(val viewState: BagProductViewState) : Event()
    }

    fun onClick(id: Int) {
        _uiEvent.trySend(Event.OnClick(id))
    }

    fun onMinusClick() {
        amount.value = amount.value?.minus(1)
        amount.value?.also {
            if (it > 0) {
                _uiEvent.trySend(Event.UpdateProduct(this))
            } else _uiEvent.trySend(Event.DeleteProduct(this))
        }
    }

    fun onPlusClick() {
        amount.value = amount.value?.plus(1)
        _uiEvent.trySend(Event.UpdateProduct(this))
    }

    private val _uiEvent = Channel<Event>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override fun isSameItem(other: Any): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        other as BagProductViewState
        return (
            id == other.id &&
                color == other.color &&
                size == other.size &&
                amount === other.amount
            )
    }

    override fun isSameContent(other: Any): Boolean {
        other as BagProductViewState
        return (
            imageUrl == other.imageUrl &&
                title == other.title &&
                color == other.color &&
                size == other.size &&
                amount.value == other.amount.value &&
                price == other.price
            )
    }
}
