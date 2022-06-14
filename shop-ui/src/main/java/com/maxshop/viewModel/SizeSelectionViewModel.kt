package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxshop.mapper.SizeMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.stream.SizeStream
import com.maxshop.viewState.BaseProductOptionsViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SizeSelectionViewModel @Inject constructor(
    private val sizeMapper: SizeMapper,
    private val sizeStream: SizeStream
) : BaseViewModel() {
    sealed class Event {
        object Close : Event()
    }

    private val _listSizes = MutableLiveData<List<RecyclerItem>>()
    val listSizes: LiveData<List<RecyclerItem>> get() = _listSizes

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun mapSizes(sizes: List<String>) {
        viewModelScope.launch {
            _listSizes.value = sizes.map {
                sizeMapper.toRecyclerItem(
                    sizeMapper.toSizeViewState(it).also {
                        it.uiEvent.onEach {
                            when (it) {
                                is BaseProductOptionsViewState.Event.OnClick -> {
                                    sizeStream.post(it.viewState.text)
                                    _event.value = Event.Close
                                }
                            }
                        }.launchIn(this)
                    }
                )
            }
        }
    }
}
