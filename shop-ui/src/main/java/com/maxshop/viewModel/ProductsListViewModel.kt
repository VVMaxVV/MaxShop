package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.internetshop.presentation.utils.SingleLiveEvent
import com.maxshop.mapper.SimplifiedProductMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.usecase.GetProductsCategoryUseCase
import com.maxshop.viewState.PLPItemViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsListViewModel @Inject constructor(
    private val getProductsCategory: GetProductsCategoryUseCase,
    private val simplifiedProductMapper: SimplifiedProductMapper
) : BaseViewModel() {
    private val _productsList = MutableLiveData<List<RecyclerItem>>()
    val productsList: LiveData<List<RecyclerItem>> get() = _productsList

    private val _event = SingleLiveEvent<Event>()
    val event: LiveData<Event> get() = _event

    fun getProducts(category: String) {
        compositeDisposable += getProductsCategory.execute(category)
            .subscribeOn(Schedulers.io())
            .map {
                it.map {
                    simplifiedProductMapper.toPLPItemViewState(it).also {
                        compositeDisposable += it.events.subscribe {
                            onPLPItemViewStateEvent(it)
                        }
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _productsList.value = it.map {
                        simplifiedProductMapper.toRecyclerItem(it)
                    }
                },
                onError = {
                    _event.value = Event.OnError(it)
                }
            )
    }

    private fun onPLPItemViewStateEvent(event: PLPItemViewState.Event) {
        when (event) {
            is PLPItemViewState.Event.OnClicked -> {
                _event.value = Event.OpenProduct(event.id, event.category, event.title)
            }
            is PLPItemViewState.Event.OnFavoriteClicked -> {
                _event.value = Event.FavoriteOnClicked(event.id, event.buttonState)
            }
        }
    }

    sealed class Event {
        data class OpenProduct(val id: Int, val category: String, val title: String) : Event()
        data class FavoriteOnClicked(val id: Int, val stateIsFavorite: Boolean?) : Event()
        data class OnError(val throwable: Throwable, val description: String? = null) : Event()
    }
}


