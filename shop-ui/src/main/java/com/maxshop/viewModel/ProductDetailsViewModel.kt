package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxshop.mapper.DetailedProductMapper
import com.maxshop.model.product.DetailedProduct
import com.maxshop.shop_ui.R
import com.maxshop.stream.ColorStream
import com.maxshop.stream.SizeStream
import com.maxshop.usecase.AddProductToBagUseCase
import com.maxshop.usecase.GetProductUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ProductDetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val sizeStream: SizeStream,
    private val colorStream: ColorStream,
    private val addProductToBagUseCase: AddProductToBagUseCase,
    private val detailedProductMapper: DetailedProductMapper
) : BaseViewModel() {
    var id: Int? = null

    private val _size = MutableLiveData<String?>(null)
    val size: LiveData<String?> get() = _size

    private val _color = MutableLiveData<String?>(null)
    val color: LiveData<String?> get() = _color

    private val _progressBar = MutableLiveData<Boolean>()

    private val _detailedProduct = MutableLiveData<DetailedProduct>()
    val detailedProduct: LiveData<DetailedProduct> get() = _detailedProduct

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    private val _errorVisibility = MutableLiveData(false)
    val errorVisibility: LiveData<Boolean> get() = _errorVisibility

    fun getProduct() {
        viewModelScope.launch {
            _progressBar.value = true
            _errorVisibility.value = false
            try {
                getProductUseCase.execute(
                    id ?: throw NullPointerException("Id cannot be null")
                )
                    .also {
                        _detailedProduct.value = it
                    }
            } catch (t: Throwable) {
                _event.value = Event.OnError(t)
                _errorVisibility.value = true
            }
            sizeStream.stream().onEach {
                _size.value = it
            }.launchIn(this)
            colorStream.stream().onEach {
                _color.value = it
            }.launchIn(this)
            _progressBar.value = false
        }
    }

    fun addToBag() {
        viewModelScope.launch {
            _detailedProduct.value?.let {
                try {
                    addProductToBagUseCase.execute(
                        detailedProductMapper.toBagProduct(
                            it,
                            if (it.colorsList == null) null
                            else color.value
                                ?: throw NullPointerException("Color can't be null"),
                            if (it.sizesList == null) null
                            else size.value ?: throw NullPointerException("Size can't be null")
                        )
                    )
                } catch (e: NullPointerException) {
                    _event.value = Event.ShowToast(R.string.toast_select_all_product_options)
                }
            }
        }
    }

    fun showSizes() {
        _event.value = detailedProduct.value?.sizesList?.let { it -> Event.ShowSizes(it) }
    }

    fun showColors() {
        _event.value = detailedProduct.value?.colorsList?.let { it -> Event.ShowColors(it) }
    }

    sealed class Event {
        data class ShowSizes(val sizes: List<String>) : Event()
        data class ShowColors(val colors: List<String>) : Event()
        data class OnError(val throwable: Throwable) : Event()
        data class ShowToast(val messageId: Int) : Event()
    }
}
