package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxshop.model.product.DetailedProduct
import com.maxshop.stream.SizeStream
import com.maxshop.usecase.GetProductUseCase
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val sizeStream: SizeStream
) : BaseViewModel() {
    private var productId: Int? = null
    val size = MutableLiveData<String?>(null)

    private val _progressBar = MutableLiveData<Boolean>()

    private val _product = MutableLiveData<DetailedProduct>()
    val product: LiveData<DetailedProduct> get() = _product

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun getProduct(id: Int) {
        productId = id
        job = viewModelScope.launch {
            _progressBar.value = true
            getProductUseCase.execute(id).also {
                _product.value = it
            }
            viewModelScope.launch {
                sizeStream.subject.collect {
                    size.value = it
                }
            }
            _progressBar.value = false
        }
    }

    fun showSizes() {
        _event.value = product.value?.sizesList?.let { it -> Event.ShowSizes(it) }
    }

    fun showColors() {
        _event.value = Event.ShowColors
    }

    sealed class Event {
        data class ShowSizes(val sizes: List<String>) : Event()
        object ShowColors : Event()
    }
}