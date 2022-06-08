package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxshop.mapper.SortMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.model.TypeSort
import com.maxshop.stream.SortStream
import com.maxshop.usecase.GetSortsUseCase
import com.maxshop.utils.SingleLiveEvent
import com.maxshop.viewState.SortViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class SortsViewModel @Inject constructor(
    private val getSortsUseCase: GetSortsUseCase,
    private val sortMapper: SortMapper,
    private val sortStream: SortStream
) : BaseViewModel() {
    var activePosition = 0

    private val _listSorts = MutableLiveData<List<RecyclerItem>>()
    val listSorts: LiveData<List<RecyclerItem>> get() = _listSorts

    private val _event = SingleLiveEvent<Event>()
    val event: LiveData<Event> get() = _event

    fun getSortsList(activeSort: TypeSort) {
        compositeDisposable += getSortsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map {
                it.mapIndexed { position, typeSort ->
                    sortMapper.toViewState(typeSort, position).also {
                        if (it.type == activeSort) {
                            activePosition = position
                        }
                        compositeDisposable += it.events.subscribeBy(
                            onNext = {
                                onSortViewStateEvent(it)
                            }
                        )
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _listSorts.value = it.map { sortMapper.toRecyclerItem(it) }.also {
                        (it[activePosition].data as? SortViewState)?.isActive?.value = true
                    }
                }
            )
    }

    private fun onSortViewStateEvent(viewStateEvent: SortViewState.Event) {
        when (viewStateEvent) {
            is SortViewState.Event.OnClick -> {
                (_listSorts.value?.get(activePosition)?.data as? SortViewState)?.isActive?.value =
                    false
                viewStateEvent.viewState.isActive.value = true
                activePosition = viewStateEvent.viewState.position
                sortStream.value.post(viewStateEvent.viewState.type)
                _event.value = Event.SortTypeSelected
            }
        }
    }

    sealed class Event {
        object SortTypeSelected : Event()
    }
}
