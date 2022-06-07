package com.maxshop.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    protected var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        job?.cancel()
    }
}
