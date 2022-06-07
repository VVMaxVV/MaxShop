package com.maxshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.internetshop.presentation.utils.SingleLiveEvent
import com.maxshop.mapper.CategoryMapper
import com.maxshop.model.RecyclerItem
import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.viewState.CategoryViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoryMapper: CategoryMapper
) : BaseViewModel() {

    private val _events = SingleLiveEvent<Event>()
    val events: LiveData<Event> get() = _events

    private val _categoriesList = MutableLiveData<List<RecyclerItem>>()
    val categoriesList: LiveData<List<RecyclerItem>> get() = _categoriesList

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> get() = _progressBar

    fun getCategories() {
        compositeDisposable += getCategoriesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map {
                it.map {
                    categoryMapper.toViewState(it).also {
                        compositeDisposable += it.events.subscribeBy(
                            onNext = {
                                onCategoryViewStateEvent(it)
                            }
                        )
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _progressBar.value = true }
            .doFinally { _progressBar.value = false }
            .subscribeBy(
                onSuccess = {
                    _categoriesList.value = it.map {
                        categoryMapper.toRecyclerItem(it)
                    }
                },
                onError = {
                    _events.value =
                        Event.OnError(it)
                }
            )
    }

    private fun onCategoryViewStateEvent(event: CategoryViewState.Event) {
        when (event) {
            is CategoryViewState.Event.OnCategoryClick -> {
                _events.value =
                    Event.OpenCategory(event.name)
            }
        }
    }

    sealed class Event {
        data class OpenCategory(val categoryName: String) : Event()
        data class ShowToast(val text: String) : Event()
        data class OnError(
            val throwable: Throwable,
            val description: String? = null
        ) : Event()
    }
}
