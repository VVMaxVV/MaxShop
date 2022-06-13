package com.maxshop.viewModel

import androidx.lifecycle.LifecycleOwner
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
) : BaseLifecyclerViewModel() {
    sealed class Event {
        object Close : Event()
    }

    var sizesList: List<String> = emptyList()

    private val _itemsList = MutableLiveData<List<RecyclerItem>>()
    val itemsList: LiveData<List<RecyclerItem>> get() = _itemsList

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mapSizes(sizesList)
    }

    private fun mapSizes(sizes: List<String>) {
        viewModelScope.launch {
            _itemsList.value = sizes.map {
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
