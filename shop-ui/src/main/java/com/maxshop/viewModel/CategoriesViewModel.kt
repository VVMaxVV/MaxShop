package com.maxshop.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.internetshop.presentation.utils.SingleLiveEvent
import com.maxshop.mapper.CategoryMapper
import com.maxshop.usecase.GetCategoriesUseCase
import com.maxshop.viewState.CategoryViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoryMapper: CategoryMapper
) : BaseViewModel() {

    val events = SingleLiveEvent<CategoryEvents>()
    val categoriesLiveData = MutableLiveData<List<CategoryViewState>>()
    val progressBar = MutableLiveData<Boolean>()

    fun getProducts() {
        getCategoriesUseCase.execute()
            .timeout(60, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.value = true }
            .doFinally {
                progressBar.value = false
            }
            .subscribe(
                { it ->
                    categoriesLiveData.value = it.map {
                        categoryMapper.toViewState(it).also {
                            compositeDisposable.add(
                                it.events.subscribe {
                                    when (it) {
                                        is CategoryViewState.Event.OnProductClick -> {
                                            events.value =
                                                CategoryEvents.OpenCategoryProductListEvents(it.name)
                                        }
                                    }
                                }
                            )
                        }
                    }
                },
                {
                    events.value =
                        CategoryEvents.ReceivedThrowable(it, "GetCategoriesUseCase Throwable")
                }
            ).run(compositeDisposable::add)
    }

    sealed class CategoryEvents {
        data class OpenCategoryProductListEvents(val categoryName: String) : CategoryEvents()
        data class ToastCategoryEvents(val text: String) : CategoryEvents()
        data class ReceivedThrowable(val throwable: Throwable, val description: String? = null) :
            CategoryEvents()
    }
}
