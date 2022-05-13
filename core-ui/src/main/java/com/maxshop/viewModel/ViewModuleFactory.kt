package com.maxshop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModuleFactory @Inject constructor(
    private val viewModelFactory: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelFactory.getValue(modelClass as Class<ViewModel>).get() as T
    }
}
