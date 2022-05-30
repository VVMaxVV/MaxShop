package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.internetshop.presentation.utils.SingleLiveEvent
import com.maxshop.SortStream
import com.maxshop.mapper.SimplifiedProductMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.model.TypeSort
import com.maxshop.usecase.GetProductsCategoryUseCase
import com.maxshop.viewState.PLPItemViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class ProductsListViewModel @Inject constructor(
    private val getProductsCategory: GetProductsCategoryUseCase,
    private val simplifiedProductMapper: SimplifiedProductMapper,
    private val sortStream: SortStream
) : BaseViewModel() {
    private val _productsList = MutableLiveData<List<RecyclerItem>>()
    val productsList: LiveData<List<RecyclerItem>> get() = _productsList

    private val _event = SingleLiveEvent<Event>()
    val event: LiveData<Event> get() = _event

    val progressBar = MutableLiveData<Boolean>()

    val sort = MutableLiveData<TypeSort>()

    var categoryName: String? = null

    fun getActiveSort() {
        compositeDisposable += sortStream.value.stream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    sort.value = it
                    getProducts()
                }
            )
    }

    fun getProducts() {
        categoryName?.let { name ->
            compositeDisposable += getProductsCategory.execute(name, sort.value ?: TypeSort.Popular)
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
                .doOnSubscribe { progressBar.value = true }
                .doFinally { progressBar.value = false }
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
    }

    fun showSorts() {
        _event.value = Event.ShowSorts
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
        object ShowSorts : Event()
    }
}
