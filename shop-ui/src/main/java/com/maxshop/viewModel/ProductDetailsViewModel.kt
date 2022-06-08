package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxshop.model.product.DetailedProduct
import com.maxshop.stream.ColorStream
import com.maxshop.stream.SizeStream
import com.maxshop.usecase.GetProductUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ProductDetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val sizeStream: SizeStream,
    private val colorStream: ColorStream
) : BaseViewModel() {
    private var productId: Int? = null

    private val _size = MutableLiveData<String?>(null)
    val size: LiveData<String?> get() = _size

    private val _color = MutableLiveData<String?>(null)
    val color: LiveData<String?> get() = _color

    private val _progressBar = MutableLiveData<Boolean>()

    private val _product = MutableLiveData<DetailedProduct>()
    val product: LiveData<DetailedProduct> get() = _product

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun getProduct(id: Int) {
        productId = id
        viewModelScope.launch {
            _progressBar.value = true
            try {
                getProductUseCase.execute(id).also {
                    _product.value = it
                }
            } catch (t: Throwable) {
                _event.value = Event.ReceivedThrowable(t)
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

    fun showSizes() {
        _event.value = product.value?.sizesList?.let { it -> Event.ShowSizes(it) }
    }

    fun showColors() {
        _event.value = product.value?.colorsList?.let { it -> Event.ShowColors(it) }
    }

    sealed class Event {
        data class ShowSizes(val sizes: List<String>) : Event()
        data class ShowColors(val colors: List<String>) : Event()
        data class ReceivedThrowable(val throwable: Throwable) : Event()
    }
}
