package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxshop.mapper.SizeMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.stream.SizeStream
import com.maxshop.viewState.SizeViewState
import javax.inject.Inject
import kotlinx.coroutines.launch

internal class SizeSelectionViewModel @Inject constructor(
    private val sizeMapper: SizeMapper,
    private val sizeStream: SizeStream
) : BaseViewModel() {

    private val _listSizes = MutableLiveData<List<RecyclerItem>>()
    val listSizes: LiveData<List<RecyclerItem>> get() = _listSizes

    fun getSizes(sizes: List<String>) {
        viewModelScope.launch {
            _listSizes.value = sizes.map {
                sizeMapper.toRecyclerItem(
                    sizeMapper.toSizeViewState(it).also {
                        viewModelScope.launch {
                            it.events.collect {
                                when (it) {
                                    is SizeViewState.Event.OnClick -> sizeStream.post(it.sizeViewState.text)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
