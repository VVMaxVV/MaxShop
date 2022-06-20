package com.maxshop.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.maxshop.stream.BottomNavMenuStream
import com.maxshop.viewModel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var factory: ViewModelFactory

    @Inject
    lateinit var bottomNavMenuStream: BottomNavMenuStream

    protected fun showToastMessage(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text, duration).show()
    }

    open fun getBottomNavVisibility(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this is HasLifeCycleObserver) {
            addLifecycleObserver()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavMenuStream.post(getBottomNavVisibility())
    }
}
