package com.maxshop.fragment

import android.widget.Toast
import com.maxshop.viewModel.ViewModuleFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var factory: ViewModuleFactory

    protected fun showToastMessage(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text, duration).show()
    }
}
