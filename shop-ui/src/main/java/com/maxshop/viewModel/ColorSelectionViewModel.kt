package com.maxshop.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxshop.mapper.ColorMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.stream.ColorStream
import com.maxshop.utils.SingleLiveEvent
import com.maxshop.viewState.BaseProductOptionsViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ColorSelectionViewModel @Inject constructor(
    private val colorMapper: ColorMapper,
    private val colorStream: ColorStream
) : BaseLifecycleViewModel() {
    sealed class Event {
        object Close : Event()
    }

    var colorsList: List<String> = emptyList()

    private val _itemsList = MutableLiveData<List<RecyclerItem>>()
    val itemsList: LiveData<List<RecyclerItem>> get() = _itemsList

    private val _event = SingleLiveEvent<Event>()
    val event: LiveData<Event> get() = _event

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mapColors(colorsList)
    }

    private fun mapColors(colors: List<String>) {
        viewModelScope.launch {
            _itemsList.value = colors.map {
                colorMapper.toRecyclerItem(
                    colorMapper.toSizeViewState(it).also {
                        viewModelScope.launch {
                            it.uiEvent.collect {
                                when (it) {
                                    is BaseProductOptionsViewState.Event.OnClick -> {
                                        colorStream.post(it.viewState.text)
                                        _event.value = Event.Close
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
