package com.maxshop.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bag_ui.R
import com.maxshop.mapper.BagProductMapper
import com.maxshop.mapper.BagProductViewStateMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.usecase.DeleteProductFromBagUseCase
import com.maxshop.usecase.GetBagProductsUseCase
import com.maxshop.usecase.UpdateBagProductUseCase
import com.maxshop.util.formatPrice
import com.maxshop.utils.SingleLiveEvent
import com.maxshop.viewState.BagProductViewState
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.NullPointerException

internal class BagViewModel @Inject constructor(
    private val getBagProductsUseCase: GetBagProductsUseCase,
    private val bagProductMapper: BagProductMapper,
    private val deleteProductFromBagUseCase: DeleteProductFromBagUseCase,
    private val updateBagProductUseCase: UpdateBagProductUseCase,
    private val bagProductViewStateMapper: BagProductViewStateMapper
) : BaseViewModel() {

    sealed class Event {
        data class OpenProduct(val id: Int) : Event()
        data class ShowToast(@StringRes val idString: Int) : Event()
    }

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> get() = _progressBar

    private val _productsList = MutableLiveData<List<RecyclerItem>>()
    val productsList: LiveData<List<RecyclerItem>> get() = _productsList

    private val _totalPrice = MediatorLiveData<String>()
    val totalPrice: LiveData<String> get() = _totalPrice

    private val _event = SingleLiveEvent<Event>()
    val event: LiveData<Event> get() = _event

    fun getProducts() {
        viewModelScope.launch {
            _progressBar.value = true
            getBagProductsUseCase.execute().map {
                bagProductMapper.toViewState(it).also {
                    collectEvent(it)
                }
            }.map {
                bagProductViewStateMapper.toRecyclerItem(it)
            }.also { _productsList.value = it }
            _totalPrice.value = calculatePrice().formatPrice()
            _progressBar.value = false
        }
    }

    private suspend fun collectEvent(viewState: BagProductViewState) {
        viewModelScope.launch {
            viewState.uiEvent.collect {
                handleViewStateEvent(it)
            }
        }

        _totalPrice.addSource(viewState.amount) {
            _totalPrice.value = calculatePrice().formatPrice()
        }
    }

    private suspend fun handleViewStateEvent(event: BagProductViewState.Event) {
        when (event) {
            is BagProductViewState.Event.OnClick -> {
                _event.value = Event.OpenProduct(event.id)
            }
            is BagProductViewState.Event.UpdateProduct -> {
                try {
                    updateBagProductUseCase.execute(
                        bagProductViewStateMapper.toBagProduct(event.viewState)
                    )
                } catch (e: NullPointerException) {
                    _event.value = Event.ShowToast(R.string.toast_invalid_number_products)
                }
            }
            is BagProductViewState.Event.DeleteProduct -> {
                try {
                    deleteProductFromBagUseCase.execute(
                        bagProductViewStateMapper.toBagProduct(event.viewState)
                    )
                    _productsList.value?.toMutableList()?.apply {
                        removeIf {
                            (it.data as? BagProductViewState)?.let {
                                if (it.id == event.viewState.id &&
                                    it.color == event.viewState.color &&
                                    it.size == event.viewState.size
                                ) return@removeIf true
                            }
                            return@removeIf false
                        }
                    }.also {
                        _productsList.value = it
                    }
                } catch (e: NullPointerException) {
                    _event.value = Event.ShowToast(R.string.toast_error_deleting_from_bag)
                }
                _totalPrice.value = calculatePrice().formatPrice()
            }
        }
    }

    private fun calculatePrice(): Float {
        var price = 0f
        _productsList.value?.let {
            it.forEach { recyclerItem ->
                (recyclerItem.data as? BagProductViewState)?.let {
                    it.amount.value?.times(it.price.toFloat())?.let {
                        price += it
                    }
                }
            }
        }
        return price
    }
}
