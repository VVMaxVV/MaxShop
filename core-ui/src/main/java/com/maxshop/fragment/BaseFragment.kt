package com.maxshop.fragment

import android.widget.Toast
import com.maxshop.viewModel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var factory: ViewModelFactory

    protected fun showToastMessage(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text, duration).show()
    }
}
