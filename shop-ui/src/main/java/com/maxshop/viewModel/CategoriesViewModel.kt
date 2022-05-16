package com.maxshop.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.internetshop.presentation.utils.SingleLiveEvent
import com.maxshop.mapper.CategoryMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.viewState.CategoryViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoryMapper: CategoryMapper
) : BaseViewModel() {

    val events = SingleLiveEvent<Event>()
    val categories = MutableLiveData<List<RecyclerItem>>()
    val progressBar = MutableLiveData<Boolean>()

    fun getProducts() {
        getCategoriesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.value = true }
            .doFinally { progressBar.value = false }
            .map {
                it.map {
                    categoryMapper.toViewState(it).also {
                        compositeDisposable.add(
                            it.events.subscribe {
                                when (it) {
                                    is CategoryViewState.Event.OnProductClick -> {
                                        events.value =
                                            Event.OpenEvent(it.name)
                                    }
                                }
                            }
                        )
                    }
                }
            }
            .subscribe(
                {
                    categories.value = it.map {
                        categoryMapper.toRecyclerItem(it)
                    }
                },
                {
                    events.value =
                        Event.ReceivedThrowableEvent(it, "GetCategoriesUseCase Throwable")
                }
            ).run(compositeDisposable::add)
    }

    sealed class Event {
        data class OpenEvent(val categoryName: String) : Event()
        data class ToastEvent(val text: String) : Event()
        data class ReceivedThrowableEvent(
            val throwable: Throwable,
            val description: String? = null
        ) : Event()
    }
}
