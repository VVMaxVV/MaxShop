package com.maxshop.viewModel

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
) : BaseViewModel() {
    sealed class Event {
        object Close : Event()
    }

    private val _listColors = MutableLiveData<List<RecyclerItem>>()
    val listColors: LiveData<List<RecyclerItem>> get() = _listColors

    private val _event = SingleLiveEvent<Event>()
    val event: LiveData<Event> get() = _event

    fun mapColors(sizes: List<String>) {
        viewModelScope.launch {
            _listColors.value = sizes.map {
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
