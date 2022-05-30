package com.maxshop.viewState

import androidx.lifecycle.MutableLiveData
import com.maxshop.adapter.comparator.RecyclerItemComparator
import io.reactivex.subjects.PublishSubject

class PLPItemViewState(
    val id: Int,
    val category: String,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val ratingCount: Int,
    val price: String,
    val favorite: MutableLiveData<Boolean>
) : RecyclerItemComparator {
    sealed class Event {
        data class OnClicked(val id: Int, val category: String, val title: String) : Event()
        data class OnFavoriteClicked(val id: Int, val buttonState: Boolean?) : Event()
    }

    private val uiEvent = PublishSubject.create<Event>()
    val events = uiEvent.hide()

    fun onClick(product: PLPItemViewState) {
        uiEvent.onNext(Event.OnClicked(product.id, product.category, product.title))
    }

    fun onFavoriteClick(product: PLPItemViewState) {
        uiEvent.onNext(Event.OnFavoriteClicked(product.id, product.favorite.value))
    }

    override fun isSameItem(other: Any): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false
        return id == (other as PLPItemViewState).id
    }

    override fun isSameContent(other: Any): Boolean {
        other as PLPItemViewState
        return (
            title == other.title &&
                imageUrl == other.imageUrl &&
                rating == other.rating &&
                ratingCount == other.ratingCount &&
                price == other.price
            )
    }
}
