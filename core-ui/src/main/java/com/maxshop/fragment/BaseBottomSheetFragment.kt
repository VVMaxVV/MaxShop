package com.maxshop.fragment

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.example.core_ui.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxshop.viewModel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class BaseBottomSheetFragment : BottomSheetDialogFragment() {
    @Inject
    protected lateinit var factory: ViewModelFactory

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    protected fun showToastMessage(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text, duration).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this is HasLifeCycleObserver) {
            addLifecycleObserver()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}
