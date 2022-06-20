package com.maxshop.viewModel

import androidx.lifecycle.LifecycleOwner
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
) : BaseLifecycleViewModel() {
    var id: Int? = null

    private val _size = MutableLiveData<String?>(null)
    val size: LiveData<String?> get() = _size

    private val _color = MutableLiveData<String?>(null)
    val color: LiveData<String?> get() = _color

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> get() = _progressBar

    private val _detailedProduct = MutableLiveData<DetailedProduct>()
    val detailedProduct: LiveData<DetailedProduct> get() = _detailedProduct

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    private val _errorVisibility = MutableLiveData(false)
    val errorVisibility: LiveData<Boolean> get() = _errorVisibility

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getProduct()
    }

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
    }
}
